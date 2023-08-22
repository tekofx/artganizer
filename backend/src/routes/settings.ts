import express, { Request, Response } from "express";
import "reflect-metadata";
import path from "path";
import fs from "fs";

const router = express.Router();
const settingsFile = path.join(__dirname, "../../data/settings.json");

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

export default router;
