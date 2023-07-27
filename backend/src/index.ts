import express, { NextFunction, Request, Response } from 'express';
import "reflect-metadata";
import { DataSource } from 'typeorm';
import { Artist } from './entities/Artist';
import { Submission } from './entities/Submission';
import { config } from './config';
import multer, { FileFilterCallback } from 'multer';
import * as fs from 'fs';
import * as path from 'path';
import sizeOf from 'image-size';
import submissionPostValidation from './utils';
import sharp from 'sharp';
import { AppDataSource, ArtistRepo, SubmissionRepo } from './typeorm.config';
import artist from './routes/artists';
import submission from './routes/submissions';
import cors from 'cors';
if (process.env.NODE_ENV !== 'production') {
    require('dotenv').config();
    console.log(process.env.NODE_ENV);
    console.log(process.env.FOO);

}

// Create express app
const app = express();
app.use(express.json());
app.use(cors());
const port = 3001;


app.get('/', (req: Request, res: Response) => {
    res.send('Express + TypeScript Server');
});

app.use("/artists", artist);
app.use("/submissions", submission);


app.listen(port, () => {
    console.log(`[server]: Server is running at http://localhost:${port}`);
});

AppDataSource.initialize()
    .then(() => {
        // here you can start to work with your database
    })
    .catch((error) => console.log(error));