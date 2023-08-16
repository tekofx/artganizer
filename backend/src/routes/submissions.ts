import express, { Request, Response } from "express";
import "reflect-metadata";
import { DataSource, In } from "typeorm";
import { Artist } from "../entities/Artist";
import { Submission } from "../entities/Submission";
import multer, { FileFilterCallback } from "multer";
import * as fs from "fs";
import * as path from "path";
import sharp from "sharp";
import Vibrant from "node-vibrant";
import {
  ArtistRepo,
  SubmissionRepo,
  TagRepo,
  FolderRepo,
  CharacterRepo,
} from "../typeorm.config";
import sizeOf from "image-size";

const submissionsDir = path.join(__dirname, "../../uploads/submissions");
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

const uploadSubmissions = multer({
  storage: submissionsStorage,
  fileFilter: fileFilter,
});

const router = express.Router();

router.get("/", async (req: Request, res: Response) => {
  var { tags, folders, characters, artist } = req.query;

  const queryBuilder = SubmissionRepo.createQueryBuilder("submission");

  // Use leftJoinAndSelect to join the submission and tags tables and select the related tags
  queryBuilder.leftJoinAndSelect("submission.tags", "tag");

  // Use leftJoinAndSelect to join the submission and folders tables and select the related folders
  queryBuilder.leftJoinAndSelect("submission.folders", "folder");

  if (tags) {
    queryBuilder.innerJoinAndSelect(
      "submission.tags",
      "tag",
      "tag.id IN (:...tags)",
      { tags }
    );
  }

  if (folders) {
    queryBuilder.innerJoinAndSelect(
      "submission.folders",
      "folder",
      "folder.id IN (:...folders)",
      { folders }
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
  console.log(submissions);

  // Add image URL
  submissions.forEach((submission) => {
    submission.image =
      process.env.URL + "/submissions/uploads/" + submission.id;
  });

  res.send(submissions);
});

router.get("/uploads/:submissionId", async (req: Request, res: Response) => {
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

  const filePath = path.join(
    submissionsDir,
    submissionId + "." + submission.format
  );

  if (!fs.existsSync(filePath)) {
    res.status(404).send("submission file not found");
    return;
  }

  return res.sendFile(filePath);
});

router.post(
  "/",
  uploadSubmissions.single("image"),
  async (req: Request, res: Response) => {
    var { title, description, rating, artist, folders, tags, characters } =
      req.body;

    console.log("req.body");
    console.log(req.body);
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

    try {
      // Obtener las dimensiones de la imagen
      const dimensions = sizeOf(image.path);

      const submission = SubmissionRepo.create({
        title: title,
        description: description,
        rating: rating,
        width: dimensions.width,
        height: dimensions.height,
        format: dimensions.type,
        colors: colorsArray,
        size: image.size,
      });
      if (artist) {
        var artistObj = await ArtistRepo.findOne({ where: { id: artist } });
        if (artistObj) {
          submission.artist = artistObj;
        }
      }

      if (folders) {
        if (!Array.isArray(folders)) {
          folders = [folders];
        }
        var folderOjs = [];
        for (var i = 0; i < folders.length; i++) {
          var folderObj = await FolderRepo.findOne({
            where: { id: folders[i] },
          });
          if (folderObj) {
            folderOjs.push(folderObj);
          }
        }
        submission.folders = folderOjs;
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

      var id = await SubmissionRepo.save(submission)
        .then((submission) => {
          return submission.id;
        })
        .catch((error) => {
          console.log(error);
        });
      // Renombrar el archivo con el ID generado
      const tempPath = image.path;
      const newPath = path.join(
        "uploads/submissions",
        id + path.extname(image.originalname)
      );
      fs.renameSync(tempPath, newPath);

      submission.image = process.env.URL + "/submissions/uploads/" + id;

      res.send(submission);
    } catch (error) {
      fs.unlinkSync(image.path);
      console.log("error");
      return res.status(500).send("error");
    }
  }
);

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

  // Remove from uploads folder
  const filePath = path.join(
    submissionsDir,
    submissionId + "." + submission.format
  );
  fs.unlinkSync(filePath);

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
    relations: ["tags", "folders"],
  });

  if (submission == null) {
    res.status(404).send("submission not found");
    return;
  }

  var { title, description, rating, artist, tags, folders } = req.body;

  if (submission) {
    submission.title = title;
    submission.description = description;
    submission.rating = rating;
    submission.artist = artist;

    if (tags) {
      const tagEntities = await TagRepo.find({
        where: {
          id: In(tags),
        },
      });
      submission.tags = tagEntities;
    }

    if (folders) {
      const folderEntities = await FolderRepo.find({
        where: {
          id: In(folders),
        },
      });
      submission.folders = folderEntities;
    }

    await SubmissionRepo.save(submission);
  }

  res.send(submission);
});

export default router;
