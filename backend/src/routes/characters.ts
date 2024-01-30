import express, { Request, Response } from "express";
import * as fs from "fs";
import multer, { FileFilterCallback } from "multer";
import * as path from "path";
import "reflect-metadata";
import sharp from "sharp";
import { CharacterRepo } from "../typeorm.config";
const charactersDir = "backend/data/uploads/characters";
if (!fs.existsSync(charactersDir)) {
  fs.mkdirSync(charactersDir, { recursive: true });
}

// Multer config
const submissionsStorage = multer.diskStorage({
  destination: function (
    req: Express.Request,
    file: Express.Multer.File,
    cb: (error: Error | null, destination: string) => void
  ) {
    cb(null, charactersDir);
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
const uploadCharacterPic = multer({
  storage: submissionsStorage,
  fileFilter: fileFilter,
});
const router = express.Router();

router.get("/", async (req: Request, res: Response) => {
  const characters = await CharacterRepo.find();
  res.send(characters);
});

router.get("/:characterId", async (req: Request, res: Response) => {
  if (req.params.characterId == null) {
    res.status(400).send("Character ID not provided");
    return;
  }

  var characterId: number = parseInt(req.params.characterId);
  const queryBuilder = CharacterRepo.createQueryBuilder("character");
  queryBuilder.where("character.id = :id", { id: characterId });

  var character = await queryBuilder.getOne();
  if (character == null) {
    res.status(404).send("Character not found");
    return;
  }

  res.send(character);
});

router.post(
  "/",
  uploadCharacterPic.single("image"),
  async (req: Request, res: Response) => {
    const { name, description } = req.body;
    var file = req.file;
    const character = CharacterRepo.create({ name, description });
    var id = await CharacterRepo.save(character)
      .then((character) => {
        return character.id;
      })
      .catch((error) => {
        console.log(error);
      });

    if (file) {
      await sharp(file.path)
        .resize(500)
        .jpeg()
        .toFile(path.join(charactersDir, id + ".jpg"))
        .then(() => {
          // Eliminar el archivo temporal
          if (file?.path) {
            fs.unlinkSync(file.path);
          }
        })
        .catch((error) => {
          console.log(error);
        });
      character.image = "/api/characters/" + id;
      await CharacterRepo.save(character);
    }

    res.send(character);
  }
);

router.use("/uploads", express.static(charactersDir));

router.put(
  "/:characterId",
  uploadCharacterPic.single("image"),
  async (req: Request, res: Response) => {
    if (req.params.characterId == null) {
      res.status(400).send("submission ID not provided");
      if (file?.path) {
        fs.unlinkSync(file.path);
      }
      return;
    }
    var characterId: number = parseInt(req.params.characterId);

    var file = req.file;

    var character = await CharacterRepo.findOne({ where: { id: characterId } });
    console.log(character);

    if (character == null) {
      res.status(404).send("character not found");
      if (file?.path) {
        fs.unlinkSync(file.path);
      }
      return;
    }
    // Remove old image
    const filePath = path.join(charactersDir, characterId + ".jpg");
    if (fs.existsSync(filePath)) {
      fs.unlinkSync(filePath);
    }

    // Convertir a JPG y Renombrar el archivo con el ID generado
    if (file) {
      await sharp(file.path)
        .jpeg()
        .toFile(path.join(charactersDir, characterId + ".jpg"))
        .then(() => {
          // Eliminar el archivo temporal
          if (file?.path) {
            fs.unlinkSync(file.path);
          }
        })
        .catch((error) => {
          console.log(error);
        });
      character.image = "/api/characters/uploads/" + characterId + ".jpg";
    }

    var { name, description } = req.body;
    character.name = name;
    character.description = description;

    var result;
    try {
      result = await CharacterRepo.save(character);

      res.send(result);
    } catch (error) {
      console.log(error);
      res.status(500).send("error");
    }
  }
);
router.delete("/:id", async (req: Request, res: Response) => {
  if (req.params.id == null) {
    res.status(400).send("artist ID not provided");
    return;
  }

  var characterId: number = parseInt(req.params.id);
  const character = await CharacterRepo.findOne({ where: { id: characterId } });

  if (character == null) {
    res.status(404).send("character not found");
    return;
  }

  const filePath = path.join(charactersDir, characterId + ".jpg");
  if (fs.existsSync(filePath)) {
    fs.unlinkSync(filePath);
  }

  // Remove from database
  await CharacterRepo.remove(character);

  res.send(character);
});

export default router;
