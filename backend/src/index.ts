import express, { Request, Response } from "express";
import "reflect-metadata";
import { AppDataSource } from "./typeorm.config";
import artist from "./routes/artists";
import submission from "./routes/submissions";
import folders from "./routes/folders";
import tags from "./routes/tags";
import characters from "./routes/characters";
import settings from "./routes/settings";
import cors from "cors";

const port = 3001;

if (process.env.NODE_ENV !== "production") {
  require("dotenv").config();
  process.env.URL = `http://localhost:${port}`;
}

// Create express app
const app = express();
app.use(express.json());
app.use(cors());

app.get("/", (req: Request, res: Response) => {
  res.send("Express + TypeScript Server");
});

app.use("/artists", artist);
app.use("/submissions", submission);
app.use("/folders", folders);
app.use("/tags", tags);
app.use("/characters", characters);
app.use("/settings", settings);

app.listen(port, () => {
  console.log(`[server]: Server is running at ${process.env.URL}`);
});

AppDataSource.initialize()
  .then(() => {
    // here you can start to work with your database
  })
  .catch((error) => console.log(error));
