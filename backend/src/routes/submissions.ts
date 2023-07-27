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



export default router;