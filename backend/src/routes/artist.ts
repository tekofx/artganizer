import express, { Request, Response } from 'express';
import "reflect-metadata";
import { DataSource } from 'typeorm';
import { Artist } from '../entities/Artist';
import { Submission } from '../entities/Submission';
import multer, { FileFilterCallback } from 'multer';
import * as fs from 'fs';
import * as path from 'path';
import sharp from 'sharp';
import { ArtistRepo, SubmissionRepo } from '../typeorm.config';

const artistsPicsDir = path.join(__dirname, '../../uploads/artistPics');
if (!fs.existsSync(artistsPicsDir)) {
    fs.mkdirSync(artistsPicsDir);
}




const router = express.Router();
// Multer config

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

const uploadArtistPics = multer({ dest: artistsPicsDir, fileFilter: fileFilter });

router.post('/', uploadArtistPics.single("file"), async (req: Request, res: Response) => {
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


    // Convertir a JPG y Renombrar el archivo con el ID generado
    if (file) {
        sharp(file.path)
            .jpeg()
            .toFile(path.join(artistsPicsDir, id + ".jpg")).then(() => {
                // Eliminar el archivo temporal
                if (file?.path) {
                    fs.unlinkSync(file.path);
                }
            }).catch((error) => {
                console.log(error);
            }
            );
    }

    res.send(artist);
});

router.get('/', async (req: Request, res: Response) => {
    const artists = await ArtistRepo.find();
    res.send(artists);
});

router.get('/:artistId', async (req: Request, res: Response) => {
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

router.delete('/:artistId', async (req: Request, res: Response) => {
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

    // Remove from uploads folder
    const filePath = path.join(artistsPicsDir, artistId + ".jpg");
    if (fs.existsSync(filePath)) {
        fs.unlinkSync(filePath);
    }

    // Remove from database
    await ArtistRepo.remove(artist);

    res.send(artist);
});

router.get('/:artistId/submissions', async (req: Request, res: Response) => {
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



export default router;