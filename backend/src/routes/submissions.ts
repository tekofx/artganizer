import express, { Request, Response } from 'express';
import "reflect-metadata";
import { DataSource } from 'typeorm';
import { Artist } from '../entities/Artist';
import { Submission } from '../entities/Submission';
import multer, { FileFilterCallback } from 'multer';
import * as fs from 'fs';
import * as path from 'path';
import sharp from 'sharp';
import { ArtistRepo, SubmissionRepo, LabelRepo, FolderRepo, CharacterRepo } from '../typeorm.config';
import sizeOf from 'image-size';


const submissionsDir = path.join(__dirname, '../../uploads/submissions');
if (!fs.existsSync(submissionsDir)) {
    fs.mkdirSync(submissionsDir);
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


const router = express.Router();

router.get('/', async (req: Request, res: Response) => {
    const submissions = await SubmissionRepo.find();

    // Add image URL
    submissions.forEach((submission) => {
        submission.image = process.env.URL + "/submissions/uploads/" + submission.id;
    });
    res.send(submissions);
});

router.get('/uploads/:submissionId', async (req: Request, res: Response) => {
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

    const filePath = path.join(submissionsDir, submissionId + "." + submission.format);

    if (!fs.existsSync(filePath)) {
        res.status(404).send("submission file not found");
        return;
    }

    return res.sendFile(filePath);
});

router.post('/', uploadSubmissions.single("file"), async (req: Request, res: Response) => {

    var { title, description, rating, artist, folders, labels, characters } = req.body;
    var file = req.file;

    if (!file) {
        res.status(400).send("File not provided or not an image");
        return;
    }


    // Obtener las dimensiones de la imagen
    const dimensions = sizeOf(file.path);

    const submission = SubmissionRepo.create(
        {
            title: title, description: description, rating: rating,
            width: dimensions.width, height: dimensions.height,
            format: dimensions.type
        }
    );
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
        for (var i = 0; i < folders.length; i++) {
            var folderObj = await FolderRepo.findOne({ where: { id: folders[i] } });
            if (folderObj) {
                folders[i] = folderObj;
            }
        }
        submission.folders = folders;
    }

    if (labels) {
        if (!Array.isArray(labels)) {
            labels = [labels];
        }
        for (var i = 0; i < labels.length; i++) {
            var labelObj = await LabelRepo.findOne({ where: { id: labels[i] } });
            if (labelObj) {
                labels[i] = labelObj;
            }
        }
        submission.labels = labels;
    }

    if (characters) {
        if (!Array.isArray(characters)) {
            characters = [characters];
        }
        for (var i = 0; i < characters.length; i++) {
            var characterObj = await CharacterRepo.findOne({ where: { id: characters[i] } });
            console.log(characterObj);
            if (characterObj) {
                characters[i] = characterObj;
            }
        }
        submission.characters = characters;
    }

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

router.delete('/:submissionId', async (req: Request, res: Response) => {
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

router.put('/:submissionId', async (req: Request, res: Response) => {
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

    var { title, description, rating, artist, labels } = req.body;
    await SubmissionRepo.update(submission, { title: title, description: description, rating: rating, artist: artist });

    if (labels) {
        for (var i = 0; i < labels.length; i++) {
            var labelObj = await LabelRepo.findOne({ where: { id: labels[i] } });
            if (labelObj) {
                console.log(labelObj);
                labels[i] = labelObj;
            }
        }
        submission.labels = labels;
        await SubmissionRepo.save(submission);
    }

    res.send(submission);
});



export default router;