import express, { Request, Response } from "express";
import * as fs from "fs";
import sizeOf from "image-size";
import multer, { FileFilterCallback } from "multer";
import Vibrant from "node-vibrant";
import * as path from "path";
import "reflect-metadata";
import sharp from "sharp";
import { Submission } from "../entities";
import {
  ArtistRepo,
  CharacterRepo,
  SubmissionRepo,
  TagRepo,
} from "../typeorm.config";

const submissionsDir = path.join(__dirname, "../../data/uploads/submissions");
if (!fs.existsSync(submissionsDir)) {
  fs.mkdirSync(submissionsDir, { recursive: true });
}

// Multer config
const submissionsStorage = multer.diskStorage({
  destination: function (
    req: Express.Request,
    file: Express.Multer.File,
    cb: (error: Error | null, destination: string) => void
  ) {
    cb(null, submissionsDir);
  },
  filename: function (
    req: Express.Request,
    file: Express.Multer.File,
    cb: (error: Error | null, filename: string) => void
  ) {
    // Guardar el archivo con un nombre temporal
    //cb(null, Date.now() + path.extname(file.originalname));
    cb(null, file.originalname);
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

const uploadSubmissions = multer({
  storage: submissionsStorage,
  fileFilter: fileFilter,
});

const router = express.Router();

router.get("/", async (req: Request, res: Response) => {
  var { tags, characters, artist } = req.query;

  const queryBuilder = SubmissionRepo.createQueryBuilder("submission");

  // Use leftJoinAndSelect to join the submission and tags tables and select the related tags
  queryBuilder.leftJoinAndSelect("submission.tags", "tag");

  // Use leftJoinAndSelect to join the submission and artist tables and select the related artist
  queryBuilder.leftJoinAndSelect("submission.artist", "artist");

  // Use leftJoinAndSelect to join the submission and characters tables and select the related characters
  queryBuilder.leftJoinAndSelect("submission.characters", "character");

  if (tags) {
    queryBuilder.innerJoinAndSelect(
      "submission.tags",
      "tag",
      "tag.id IN (:...tags)",
      { tags }
    );
  }

  if (characters) {
    queryBuilder.innerJoinAndSelect(
      "submission.characters",
      "character",
      "character.id IN (:...characters)",
      { characters }
    );
  }

  if (artist) {
    queryBuilder.andWhere("submission.artistId = :artist", { artist });
  }

  const submissions = await queryBuilder.getMany();

  res.send(submissions);
});

router.get("/:submissionId", async (req: Request, res: Response) => {
  var { tags, characters, artist } = req.query;
  if (req.params.submissionId == null) {
    res.status(400).send("submission ID not provided");
    return;
  }

  var submissionId: number = parseInt(req.params.submissionId);

  const queryBuilder = SubmissionRepo.createQueryBuilder("submission");

  queryBuilder.where("submission.id = :id", { id: submissionId });

  // Use leftJoinAndSelect to join the submission and tags tables and select the related tags
  queryBuilder.leftJoinAndSelect("submission.tags", "tag");

  // Use leftJoinAndSelect to join the submission and artist tables and select the related artist
  queryBuilder.leftJoinAndSelect("submission.artist", "artist");

  // Use leftJoinAndSelect to join the submission and characters tables and select the related characters
  queryBuilder.leftJoinAndSelect("submission.characters", "character");

  if (tags) {
    queryBuilder.innerJoinAndSelect(
      "submission.tags",
      "tag",
      "tag.id IN (:...tags)",
      { tags }
    );
  }

  if (characters) {
    queryBuilder.innerJoinAndSelect(
      "submission.characters",
      "character",
      "character.id IN (:...characters)",
      { characters }
    );
  }

  if (artist) {
    queryBuilder.andWhere("submission.artistId = :artist", { artist });
  }

  var submission = await queryBuilder.getOne();
  if (submission == null) {
    res.status(404).send("submission not found");
    return;
  }

  res.send(submission);
});

router.post(
  "/",
  uploadSubmissions.single("image"),
  async (req: Request, res: Response) => {
    var { title, description, rating, artist, tags, characters } = req.body;

    var image = req.file;
    if (!image) {
      res.status(400).send("image not provided or not an image");
      return;
    }
    var colorsArray: string[] = [];

    // Get colors
    await Vibrant.from(image.path).getPalette((err, palette) => {
      for (const colorName in palette) {
        const color = palette[colorName];
        if (color) {
          const hex = color.hex;
          colorsArray.push(hex);
        }
      }
    });

    var submission: Submission;
    try {
      // Obtener las dimensiones de la imagen
      const dimensions = sizeOf(image.path);

      submission = SubmissionRepo.create({
        title: title,
        description: description,
        rating: rating,
        width: dimensions.width,
        height: dimensions.height,
        format: dimensions.type,
        colors: colorsArray,
        size: image.size,
        filename: image.originalname,
        image: "",
        thumbnail: "",
        original_image: "",
      });
    } catch (error) {
      console.log(error);
      return res.status(400).send("Error creating submission");
    }

    if (artist) {
      var artistObj = await ArtistRepo.findOne({ where: { id: artist } });
      if (artistObj) {
        submission.artist = artistObj;
      }
    }

    if (tags) {
      if (!Array.isArray(tags)) {
        tags = [tags];
      }
      var tagObjs = [];
      for (var i = 0; i < tags.length; i++) {
        var tagObj = await TagRepo.findOne({ where: { id: tags[i] } });
        if (tagObj) {
          tagObjs.push(tagObj);
        }
      }
      submission.tags = tagObjs;
    }

    try {
      if (characters) {
        if (!Array.isArray(characters)) {
          characters = [characters];
        }
        var characterObjs = [];
        for (var i = 0; i < characters.length; i++) {
          var characterObj = await CharacterRepo.findOne({
            where: { id: characters[i] },
          });
          if (characterObj) {
            characterObjs.push(characterObj);
          }
        }
        submission.characters = characterObjs;
      }
    } catch (error) {
      console.log(error);
    }

    var id = await SubmissionRepo.save(submission)
      .then((submission) => {
        return submission.id;
      })
      .catch((error) => {
        console.log(error);
      });

    // Crear carpeta con ID submission
    const submissionDir = "data/uploads/submissions/" + submission.id + "/";
    const submissionPath = submissionDir + image.originalname;

    await fs.promises.mkdir(submissionDir);

    // Mover archivo a carpeta
    await fs.promises.rename(
      "data/uploads/submissions/" + image.originalname,
      submissionDir + image.originalname
    );

    var thumbnailSize = 300;
    var imageSize;
    if (submission.width > 1000 || submission.height > 1000) {
      imageSize = 1000;
    } else {
      imageSize = submission.width;
    }
    // Crear copias
    sharp(submissionPath)
      .resize(thumbnailSize)
      .jpeg()
      .toFile(path.join(submissionDir, "thumbnail.jpg"))
      .catch((error) => {
        console.log(error);
      });

    sharp(submissionPath)
      .resize(imageSize)
      .jpeg()
      .toFile(path.join(submissionDir, "image.jpg"))
      .catch((error) => {
        console.log(error);
      });

    submission.image = `${process.env.URL}/submissions/uploads/${id}/image.jpg`;
    submission.thumbnail = `${process.env.URL}/submissions/uploads/${id}/thumbnail.jpg`;
    submission.original_image = `${process.env.URL}/submissions/uploads/${id}/${image.originalname}`;
    await SubmissionRepo.save(submission);

    res.send(submission);
  }
);
router.use("/uploads", express.static(submissionsDir));

router.delete("/:submissionId", async (req: Request, res: Response) => {
  if (req.params.submissionId == null) {
    res.status(400).send("submission ID not provided");
    return;
  }

  var submissionId: number = parseInt(req.params.submissionId);
  const submission = await SubmissionRepo.findOne({
    where: { id: submissionId },
  });

  if (submission == null) {
    res.status(404).send("submission not found");
    return;
  }

  try {
    // Remove from uploads folder
    const filePath = path.join(submissionsDir, submissionId.toString());
    fs.rmSync(filePath, { recursive: true, force: true });
  } catch (error) {
    console.log(error);
  }

  // Remove from database
  await SubmissionRepo.remove(submission);

  res.send(submission);
});

router.put("/:submissionId", async (req: Request, res: Response) => {
  if (req.params.submissionId == null) {
    res.status(400).send("submission ID not provided");
    return;
  }

  var submissionId: number = parseInt(req.params.submissionId);
  const submission = await SubmissionRepo.findOne({
    where: { id: submissionId },
    relations: ["tags"],
  });

  if (submission == null) {
    res.status(404).send("submission not found");
    return;
  }

  var { title, description, rating, artist, tags, characters } =
    req.body.submission;

  submission.tags = tags;
  submission.characters = characters;

  // Actualizar los campos del submission
  submission.title = title;
  submission.description = description;
  submission.rating = rating;
  submission.artist = artist;

  // Guardar el submission actualizado en la base de datos
  const result = await SubmissionRepo.save(submission);

  // Enviar el submission actualizado como respuesta
  res.send(result);
});

export default router;
