import express, { Request, Response } from "express";
import fs from "fs";
import "reflect-metadata";

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

export default router;
