import express, { NextFunction, Request, Response } from 'express';
import "reflect-metadata";
import { DataSource } from 'typeorm';
import { Artist } from './entity/Artist';
import { Submission } from './entity/Submission';
import bcrypt from 'bcrypt';
import jwt, { JwtPayload } from 'jsonwebtoken';
import { config } from './config';
import multer, { FileFilterCallback } from 'multer';
import * as fs from 'fs';
import * as path from 'path';
import sizeOf from 'image-size';
import submissionPostValidation from './utils';


const submissionsDir = path.join(__dirname, '../uploads/submissions');
const artistsPicsDir = path.join(__dirname, '../uploads/artistPics');

if (!fs.existsSync(submissionsDir)) {
    fs.mkdirSync(submissionsDir);
}

if (!fs.existsSync(artistsPicsDir)) {
    fs.mkdirSync(artistsPicsDir);
}

if (process.env.NODE_ENV !== 'production') {
    require('dotenv').config();
    console.log(process.env.NODE_ENV);
    console.log(process.env.FOO);

}

// Multer config
const submissionsStorage = multer.diskStorage({
    destination: function (req: Express.Request, file: Express.Multer.File, cb: (error: Error | null, destination: string) => void) {
        cb(null, submissionsDir)
    },
    filename: function (req: Express.Request, file: Express.Multer.File, cb: (error: Error | null, filename: string) => void) {
        // Guardar el archivo con un nombre temporal
        cb(null, Date.now() + path.extname(file.originalname))
    }
})

// Función de filtro de archivos
const fileFilter = (req: Express.Request, file: Express.Multer.File, cb: FileFilterCallback) => {
    // Verificar si el archivo es una imagen
    if (file.mimetype.startsWith('image/')) {
        // Aceptar el archivo
        cb(null, true);
    } else {
        // Rechazar el archivo
        cb(null, false);
    }
}

const uploadSubmissions = multer({ storage: submissionsStorage, fileFilter: fileFilter });
const uploadArtistPics = multer({ dest: artistsPicsDir, fileFilter: fileFilter });


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

app.post('/artist', uploadArtistPics.single("file"), async (req: Request, res: Response) => {
    const artist = new Artist();
    var { name, description, socials } = req.body;
    var file = req.file;
    if (name == null) {
        res.status(400).send("name not provided");
        return;
    }

    artist.name = name;

    switch (true) {

        case description != null:
            artist.description = description;
        case socials != null:
            artist.socials = socials;
            break;
        default:
            break;
    }

    var id = await ArtistRepo.save(artist).then((artist) => {
        return artist.id;
    }).catch((error) => {
        console.log(error);
    });

    // Renombrar el archivo con el ID generado
    if (file) {
        const tempPath = file.path;
        const newPath = path.join('uploads/artistPics', id + path.extname(file.originalname));
        fs.renameSync(tempPath, newPath);
    }

    res.send(artist);
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


app.post('/submission', uploadSubmissions.single("file"), async (req: Request, res: Response) => {

    var { title, description } = req.body;
    var file = req.file;

    if (!file) {
        res.status(400).send("File not provided or not an image");
        return;
    }


    // Obtener las dimensiones de la imagen
    const dimensions = sizeOf(file.path);

    const submission = new Submission();
    submission.title = title;
    submission.description = description;
    submission.height = dimensions.height || 0;
    submission.width = dimensions.width || 0;
    submission.format = dimensions.type || "";

    var id = await SubmissionRepo.save(submission).then((submission) => {
        return submission.id;
    }).catch((error) => {
        console.log(error);

    });

    // Renombrar el archivo con el ID generado
    const tempPath = file.path;
    const newPath = path.join('uploads/submissions', id + path.extname(file.originalname));
    fs.renameSync(tempPath, newPath);

    res.send(submission);
});

app.delete('/submission/:submissionId', async (req: Request, res: Response) => {
    if (req.params.submissionId == null) {
        res.status(400).send("submission ID not provided");
        return;
    }

    var submissionId: number = parseInt(req.params.submissionId);
    const submission = await SubmissionRepo.findOne({ where: { id: submissionId } });

    if (submission == null) {
        res.status(404).send("submission not found");
        return;
    }

    // Remove from uploads folder
    const filePath = path.join(submissionsDir, submissionId + "." + submission.format);
    fs.unlinkSync(filePath);

    // Remove from database
    await SubmissionRepo.remove(submission);

    res.send(submission);
});

app.put('/submission/:submissionId', async (req: Request, res: Response) => {
    if (req.params.submissionId == null) {
        res.status(400).send("submission ID not provided");
        return;
    }

    var submissionId: number = parseInt(req.params.submissionId);
    const submission = await SubmissionRepo.findOne({ where: { id: submissionId } });

    if (submission == null) {
        res.status(404).send("submission not found");
        return;
    }

    var { title, description, rating, artist } = req.body;

    switch (true) {
        case title != null:
            submission.title = title;
        case description != null:
            submission.description = description;
        case rating != null:
            submission.rating = rating;
        case artist != null:
            submission.artist = artist;
            break;
        default:
            break;
    }

    await SubmissionRepo.save(submission);
    res.send(submission);
});

app.get('/submissions', async (req: Request, res: Response) => {
    const submissions = await SubmissionRepo.find();
    res.send(submissions);
});


app.listen(port, () => {
    console.log(`[server]: Server is running at http://localhost:${port}`);
});

AppDataSource.initialize()
    .then(() => {
        // here you can start to work with your database
    })
    .catch((error) => console.log(error));