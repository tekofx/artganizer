import express, { NextFunction, Request, Response } from 'express';
import "reflect-metadata";
import { DataSource } from 'typeorm';
import { Artist } from './entity/Artist';
import { Submission } from './entity/Submission';
import bcrypt from 'bcrypt';
import jwt, { JwtPayload } from 'jsonwebtoken';
import { config } from './config';
import { authenticateMiddleware, CustomRequest } from './middleware/Auth';
import multer from 'multer';
import * as fs from 'fs';
import * as path from 'path';
import sizeOf from 'image-size';
import submissionPostValidation from './utils';


if (process.env.NODE_ENV !== 'production') {
    require('dotenv').config();
    console.log(process.env.NODE_ENV);
    console.log(process.env.FOO);

}

// Multer config
const storage = multer.diskStorage({
    destination: function (req, file, cb) {
        cb(null, 'uploads/')
    },
    filename: function (req, file, cb) {
        // Guardar el archivo con un nombre temporal
        cb(null, Date.now() + path.extname(file.originalname))
    }
})
const upload = multer({ storage: storage });


// Create express app
const app = express();
app.use(express.json());
const port = 3001;

// Create a connection to the database
const AppDataSource = new DataSource({
    type: "mysql",
    host: process.env.MYSQL_HOST,
    port: 3306,
    username: process.env.MYSQL_USER,
    password: process.env.MYSQL_PASSWORD,
    database: process.env.MYSQL_DATABASE,
    entities: [Artist, Submission],
    synchronize: true,
    logging: false,
})

// Get repositories of db entities
const ArtistRepo = AppDataSource.manager.getRepository(Artist);
const SubmissionRepo = AppDataSource.manager.getRepository(Submission);

app.get('/', (req: Request, res: Response) => {
    res.send('Express + TypeScript Server');
});

app.get('/artists', async (req: Request, res: Response) => {
    const artists = await ArtistRepo.find();
    res.send(artists);
});

app.get('/artist/:artistId', async (req: Request, res: Response) => {
    if (req.params.artistId == null) {
        res.status(400).send("artist ID not provided");
        return;
    }

    var artistId: number = parseInt(req.params.artistId);
    const artist = await ArtistRepo.findOne({ where: { id: artistId }, relations: ["submissions"] });


    if (artist == null) {
        res.status(404).send("artist not found");
        return;
    }

    res.send(artist);
});

app.get('/artist/:artistId/submissions', async (req: Request, res: Response) => {
    if (req.params.artistId == null) {
        res.status(400).send("artist ID not provided");
        return;
    }

    var artistId: number = parseInt(req.params.artistId);
    const artist = await ArtistRepo.findOne({ where: { id: artistId } });

    if (artist == null) {
        res.status(404).send("artist not found");
        return;
    }
    const submissions = await SubmissionRepo.findBy({ artist: artist });


    res.send(submissions);
});

app.post('/artist', async (req: Request, res: Response) => {
    const artist = new Artist();
    var { name } = req.body;
    if (name == null) {
        res.status(400).send("name not provided");
        return;
    }
    artist.name = name;
    await ArtistRepo.save(artist);
    res.send(artist);
});


app.post('/artist/:artistId/submission', upload.single("file"), async (req: Request, res: Response) => {

    var { title, description } = req.body;
    var file = req.file;

    if (!file) {
        res.status(400).send("File not provided");
        return;
    }


    // Obtener las dimensiones de la imagen
    const dimensions = sizeOf(file.path);
    console.log(dimensions.width, dimensions.height);
    const submission = new Submission();
    submission.title = title;
    submission.description = description;
    submission.height = dimensions.height || 0;
    submission.width = dimensions.width || 0;

    var id = await SubmissionRepo.save(submission).then((submission) => {
        return submission.id;
    }).catch((error) => {
        console.log(error);

    });

    // Renombrar el archivo con el ID generado
    const tempPath = file.path;
    const newPath = path.join('uploads', id + path.extname(file.originalname));
    fs.renameSync(tempPath, newPath);

    res.send(submission);
});



app.listen(port, () => {
    console.log(`[server]: Server is running at http://localhost:${port}`);
});

AppDataSource.initialize()
    .then(() => {
        // here you can start to work with your database
    })
    .catch((error) => console.log(error));