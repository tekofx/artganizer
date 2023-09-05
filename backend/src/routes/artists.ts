import express, { Request, Response } from "express";
import "reflect-metadata";
import multer, { FileFilterCallback } from "multer";
import * as fs from "fs";
import * as path from "path";
import sharp from "sharp";
import { ArtistRepo, SubmissionRepo, SocialRepo } from "../typeorm.config";

const artistsPicsDir = path.join(__dirname, "../../data/uploads/artistPics");
if (!fs.existsSync(artistsPicsDir)) {
  fs.mkdirSync(artistsPicsDir, { recursive: true });
}

// Multer config
const artistsStorage = multer.diskStorage({
  destination: function (
    req: Express.Request,
    file: Express.Multer.File,
    cb: (error: Error | null, destination: string) => void
  ) {
    cb(null, artistsPicsDir);
  },
  filename: function (
    req: Express.Request,
    file: Express.Multer.File,
    cb: (error: Error | null, filename: string) => void
  ) {
    // Guardar el archivo con un nombre temporal
    cb(null, Date.now() + path.extname(file.originalname));
  },
});
// Función de filtro de archivos
const fileFilter = (
  req: Express.Request,
  file: Express.Multer.File,
  cb: FileFilterCallback
) => {
  // Verificar si el archivo es una imagen
  if (file.mimetype.startsWith("image/")) {
    // Aceptar el archivo
    cb(null, true);
  } else {
    // Rechazar el archivo
    cb(null, false);
  }
};

const uploadArtistPics = multer({
  storage: artistsStorage,
  fileFilter: fileFilter,
});

const router = express.Router();

router.get("/", async (req: Request, res: Response) => {
  const queryBuilder = ArtistRepo.createQueryBuilder("artist")
    .leftJoinAndSelect("artist.socials", "social")
    .orderBy("artist.id", "ASC");

  const artists = await queryBuilder.getMany();
  // Add image URL
  for (const artist of artists) {
    console.log(artist.socials);
    artist.image = process.env.URL + "/artists/uploads/" + artist.id;
  }
  res.send(artists);
  console.log(artists);
});

router.post(
  "/",
  uploadArtistPics.single("image"),
  async (req: Request, res: Response) => {
    var { name, description, socials } = req.body;
    var file = req.file;
    if (name == null) {
      res.status(400).send("name not provided");
      return;
    }

    const artist = ArtistRepo.create({ name, description });
    var id = await ArtistRepo.save(artist)
      .then((artist) => {
        return artist.id;
      })
      .catch((error) => {
        if (error.name == "ER_DATA_TOO_LONG") {
          res.status(400).send("Data too long");
        } else {
          res.status(400).send("An error ocurred");
          console.log(error);
        }
        return;
      });

    // Convertir a JPG y Renombrar el archivo con el ID generado
    if (file) {
      sharp(file.path)
        .jpeg()
        .toFile(path.join(artistsPicsDir, id + ".jpg"))
        .then(() => {
          // Eliminar el archivo temporal
          if (file?.path) {
            fs.unlinkSync(file.path);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    }

    if (socials) {
      socials = JSON.parse(socials);
      var socialsObjs = [];
      for (const social of socials) {
        console.log(social);
        const newSocial = SocialRepo.create({
          name: social.name,
          url: social.url,
          artist: artist,
        });
        await SocialRepo.save(newSocial);
        socialsObjs.push(newSocial);
      }
      artist.socials = socialsObjs;

      await ArtistRepo.save(artist);
    }
    console.log(artist);

    artist.image = process.env.URL + "/artists/data/uploads/" + artist.id;
    res.send(artist);
  }
);

router.get("/:artistId", async (req: Request, res: Response) => {
  if (req.params.artistId == null) {
    res.status(400).send("artist ID not provided");
    return;
  }

  var artistId: number = parseInt(req.params.artistId);
  const artist = await ArtistRepo.findOne({
    where: { id: artistId },
    relations: ["submissions"],
  });

  if (artist == null) {
    res.status(404).send("artist not found");
    return;
  }
  artist.image = process.env.URL + "/artists/data/uploads/" + artist.id;

  res.send(artist);
});

router.delete("/:artistId", async (req: Request, res: Response) => {
  if (req.params.artistId == null) {
    res.status(400).send("artist ID not provided");
    return;
  }

  var artistId: number = parseInt(req.params.artistId);
  const artist = await ArtistRepo.findOne({ where: { id: artistId } });

  if (artist == null) {
    res.status(404).send("artist not found");
    return;
  }

  // Remove from data/uploads folder
  const filePath = path.join(artistsPicsDir, artistId + ".jpg");
  if (fs.existsSync(filePath)) {
    fs.unlinkSync(filePath);
  }

  // Remove from database
  await ArtistRepo.remove(artist);

  res.send(artist);
});

router.get("/:artistId/submissions", async (req: Request, res: Response) => {
  if (req.params.artistId == null) {
    res.status(400).send("artist ID not provided");
    return;
  }

  var artistId: number = parseInt(req.params.artistId);
  const submissions = await SubmissionRepo.createQueryBuilder("submission")
    .leftJoinAndSelect("submission.artist", "artist")
    .where("artist.id = :id", { id: artistId })
    .getMany();

  if (submissions.length === 0) {
    res.status(404).send("artist not found or no submissions found for artist");
    return;
  }

  res.send(submissions);
});
router.get("/uploads/:artistId", async (req: Request, res: Response) => {
  if (req.params.artistId == null) {
    res.status(400).send("Artist ID not provided");
    return;
  }

  var artistId: number = parseInt(req.params.artistId);
  const artist = await ArtistRepo.findOne({
    where: { id: artistId },
  });

  if (artist == null) {
    res.status(404).send("artist not found");
    return;
  }

  const filePath = path.join(artistsPicsDir, artistId + ".jpg");

  if (!fs.existsSync(filePath)) {
    res.status(404).send("artist image not found");
    return;
  }

  return res.sendFile(filePath);
});

router.put(
  "/:artistId",
  uploadArtistPics.single("image"),
  async (req: Request, res: Response) => {
    if (req.params.artistId == null) {
      res.status(400).send("artist ID not provided");
      return;
    }
    var artistId: number = parseInt(req.params.artistId);
    const artist = await ArtistRepo.findOne({ where: { id: artistId } });

    if (artist == null) {
      res.status(404).send("artist not found");
      return;
    }
    var file = req.file;

    // Remove old image
    const filePath = path.join(artistsPicsDir, artistId + ".jpg");
    if (fs.existsSync(filePath)) {
      fs.unlinkSync(filePath);
    }

    // Convertir a JPG y Renombrar el archivo con el ID generado
    if (file) {
      sharp(file.path)
        .jpeg()
        .toFile(path.join(artistsPicsDir, artistId + ".jpg"))
        .then(() => {
          // Eliminar el archivo temporal
          if (file?.path) {
            fs.unlinkSync(file.path);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    }

    var { id, name, description } = req.body;
    artist.name = name;
    artist.description = description;
    var result = await ArtistRepo.save(artist);
    result.image = process.env.URL + "/artists/uploads/" + result.id;
    res.send(result);
  }
);
export default router;
