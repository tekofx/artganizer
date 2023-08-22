import express, { Request, Response } from "express";
import "reflect-metadata";
import { Submission } from "../entities/Submission";
import { CharacterRepo } from "../typeorm.config";
import { Folder } from "../entities/Folder";
import * as fs from "fs";
import * as path from "path";
import sharp from "sharp";
import multer, { FileFilterCallback } from "multer";
const charactersDir = path.join(__dirname, "../../data/uploads/characters");
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
      sharp(file.path)
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
    }

    character.image = process.env.URL + "/characters/" + id;
    res.send(character);
  }
);

router.get("/uploads/:characterId", async (req: Request, res: Response) => {
  if (req.params.characterId == null) {
    res.status(400).send("Character ID not provided");
    return;
  }

  var characterId: number = parseInt(req.params.characterId);
  const Character = await CharacterRepo.findOne({
    where: { id: characterId },
  });

  if (Character == null) {
    res.status(404).send("Character not found");
    return;
  }

  const filePath = path.join(charactersDir, characterId + ".jpg");

  if (!fs.existsSync(filePath)) {
    res.status(404).send("character image not found");
    return;
  }

  return res.sendFile(filePath);
});

router.put(
  "/:id",
  uploadCharacterPic.single("image"),
  async (req: Request, res: Response) => {
    if (req.params.id == null) {
      res.status(400).send("submission ID not provided");
      if (file?.path) {
        fs.unlinkSync(file.path);
      }
      return;
    }
    var characterId: number = parseInt(req.params.id);
    var file = req.file;

    var character = await CharacterRepo.findOne({
      where: { id: characterId },
    });

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
      sharp(file.path)
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
    }

    var { id, name, description } = req.body;
    character.name = name;
    character.description = description;
    var result = await CharacterRepo.save(character);
    result.image = process.env.URL + "/characters/uploads" + characterId;

    res.send(result);
  }
);

export default router;
