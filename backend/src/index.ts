import cors from "cors";
import express, { Request, Response } from "express";
import session from "express-session";
import "reflect-metadata";
import {
  artist,
  characters,
  login,
  register,
  settings,
  submission,
  tags,
} from "./routes";
import { AppDataSource } from "./typeorm.config";

const port = 3001;
var cookieSecure = true;

if (process.env.NODE_ENV !== "production") {
  require("dotenv").config();
  process.env.URL = `http://localhost:${port}`;
  cookieSecure = false;
}

// Create express app
const app = express();
app.use(express.json());

app.use(
  session({
    secret: process.env.BACKEND_SECRET_KEY || "secret",
    resave: false,
    saveUninitialized: true,
    cookie: { secure: cookieSecure },
  })
);
app.use(
  cors({
    origin: "*",
  })
);

app.get("/", (req: Request, res: Response) => {
  res.send("Express + TypeScript Server");
});

app.use("/artists", artist);
app.use("/submissions", submission);
app.use("/tags", tags);
app.use("/characters", characters);
app.use("/settings", settings);
app.use("/login", login);
app.use("/register", register);

app.listen(port, () => {
  console.log(`[server]: Server is running at http://localhost:3001`);
});

AppDataSource.initialize()
  .then(() => {
    // here you can start to work with your database
  })
  .catch((error) => console.log(error));
