import AdmZip from "adm-zip";
import archiver from "archiver";
import express, { Request, Response } from "express";
import fs from "fs";
import fsExtra from "fs-extra";
import multer, { FileFilterCallback } from "multer";
import path from "path";
import "reflect-metadata";
import {
  ArtistRepo,
  CharacterRepo,
  SocialRepo,
  SubmissionRepo,
  TagRepo,
} from "../typeorm.config";

const router = express.Router();
const defaultSettings = {
  galleryInfo: {
    title: true,
    tags: false,
    characters: false,
    artist: false,
    dimensions: true,
    date: false,
    colors: false,
    rating: false,
  },
};

const settingsFile = "backend/data/settings.json";
if (!fs.existsSync(settingsFile)) {
  fs.writeFileSync(settingsFile, JSON.stringify(defaultSettings));
}

const exportFolder = "backend/export";
if (!fs.existsSync(exportFolder)) {
  fs.mkdirSync(exportFolder);
}
const importFolder = "backend/import";
if (!fs.existsSync(importFolder)) {
  fs.mkdirSync(importFolder);
}

const exportDataFolder = `${exportFolder}/data`;
if (!fs.existsSync(exportDataFolder)) {
  fs.mkdirSync(exportDataFolder);
}

// Multer config
const importStorage = multer.diskStorage({
  destination: function (
    req: Express.Request,
    file: Express.Multer.File,
    cb: (error: Error | null, destination: string) => void
  ) {
    cb(null, importFolder);
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
  // Verificar si el archivo es un zip
  if (
    file.mimetype === "application/zip" ||
    file.mimetype === "application/x-zip-compressed" ||
    file.mimetype === "application/octet-stream"
  ) {
    // Aceptar el archivo
    console.log("File supported");
    cb(null, true);
  } else {
    // Rechazar el archivo
    console.log("File not supported");
    cb(null, false);
  }
};

const uploadImport = multer({
  storage: importStorage,
  fileFilter: fileFilter,
  limits: {
    fileSize: 1024 * 1024 * 100, // 100MB
  },
});
router.get("/", async (req: Request, res: Response) => {
  // Send content of settings.json
  const settings = JSON.parse(fs.readFileSync(settingsFile, "utf8").toString());
  res.send(settings);
});

router.put("/", async (req: Request, res: Response) => {
  // Update settings.json
  const settings = JSON.parse(fs.readFileSync(settingsFile, "utf8").toString());
  const newSettings = { ...settings, ...req.body };
  fs.writeFileSync(settingsFile, JSON.stringify(newSettings));
  res.send(newSettings);
});

router.delete("/", async (req: Request, res: Response) => {
  // Reset settings.json
  fs.writeFileSync(settingsFile, JSON.stringify(defaultSettings));
  res.send(defaultSettings);
});

router.get("/export", async (req: Request, res: Response) => {
  // Create JSONs with each entity
  var artists = await ArtistRepo.find();
  var artistsJSON = JSON.stringify(artists);

  var characters = await CharacterRepo.find();
  var charactersJSON = JSON.stringify(characters);

  var tags = await TagRepo.find();
  var tagsJSON = JSON.stringify(tags);

  var socials = await SocialRepo.find();
  var socialsJSON = JSON.stringify(socials);

  var submissions = await SubmissionRepo.find();
  var submissionsJSON = JSON.stringify(submissions);

  // Get settings from file
  const settings = JSON.parse(fs.readFileSync(settingsFile, "utf8").toString());
  var settingsJSON = JSON.stringify(settings);

  // Create JSON file
  const exportFile = `${exportDataFolder}/export.json`;
  fs.writeFileSync(
    exportFile,
    JSON.stringify({
      artists: artistsJSON,
      characters: charactersJSON,
      tags: tagsJSON,
      socials: socialsJSON,
      submissions: submissionsJSON,
      settings: settingsJSON,
    })
  );

  // Copy uploads folder with all its contents to export folder
  await fsExtra.copy("backend/data/uploads", `${exportDataFolder}/uploads`);

  // Zip the export folder
  const output = fs.createWriteStream(`${exportFolder}/export.zip`);
  const archive = archiver("zip", {
    zlib: { level: 9 }, // Sets the compression level.
  });

  // Good practice to catch warnings (ie stat failures and other non-blocking errors)
  archive.on("warning", function (err) {
    if (err.code === "ENOENT") {
      // log warning
    } else {
      // throw error
      throw err;
    }
  });

  // Good practice to catch this error explicitly
  archive.on("error", function (err) {
    throw err;
  });

  // Pipe archive data to the file
  archive.pipe(output);

  // Append directories
  archive.directory(exportDataFolder, false);

  // Finalize the archive (ie we are done appending files but streams have to finish yet)
  archive.finalize().then(() => {
    console.log("Archive finalized");
  });

  const zipFile = path.resolve(`${exportFolder}/export.zip`);
  console.log("Zip file:", zipFile);

  archive.on("end", function () {
    console.log("Archive wrote %d bytes", archive.pointer());
  });

  // Listen for all archive data to be processed
  output.on("close", function () {
    console.log(archive.pointer() + " total bytes");
    console.log(
      "archiver has been finalized and the output file descriptor has closed."
    );
    res.download(zipFile);
  });
});

router.post(
  "/import",
  uploadImport.single("backup"),
  async (req: Request, res: Response) => {
    // empty export folder

    console.log(req.headers);
    console.log("File:", req.file);
    var file = req.file;

    if (!file) {
      return res.status(400).json({ message: "No file uploaded" });
    }

    // Unzip the file
    const zip = new AdmZip(file.path);
    zip.extractAllTo(importFolder, true);

    // Read the JSON file
    const importFile = `${importFolder}/export.json`;
    const data = JSON.parse(fs.readFileSync(importFile, "utf8").toString());
    var artists = JSON.parse(data.artists);
    var characters = JSON.parse(data.characters);
    var tags = JSON.parse(data.tags);
    var socials = JSON.parse(data.socials);
    var submissions = JSON.parse(data.submissions);
    var settings = JSON.parse(data.settings);

    for (let artist of artists) {
      await ArtistRepo.save(artist);
    }

    for (let character of characters) {
      await CharacterRepo.save(character);
    }

    for (let tag of tags) {
      await TagRepo.save(tag);
    }

    for (let submission of submissions) {
      await SubmissionRepo.save(submission);
    }

    for (let social of socials) {
      await SocialRepo.save(social);
    }

    // Copy uploads folder with all its contents to data folder
    await fsExtra.copy(`${importFolder}/uploads`, "backend/data/uploads");

    // Update settings.json
    fs.writeFileSync(settingsFile, JSON.stringify(settings));

    // Remove import contents of import folder
    //fsExtra.emptyDirSync(importFolder);

    return res.json({ message: "Imported successfully" });
  }
);

export default router;
