import express, { Request, Response } from 'express';
import "reflect-metadata";
import { Submission } from '../entities/Submission';
import { LabelRepo, SubmissionRepo } from '../typeorm.config';
import { Folder } from '../entities/Folder';

const router = express.Router();


router.get('/', async (req: Request, res: Response) => {
    const labels = await LabelRepo.find();
    res.send(labels);
});


router.post('/', async (req: Request, res: Response) => {
    const { name } = req.body;
    const label = LabelRepo.create({ name });
    await LabelRepo.save(label);
    res.send(label);
});

export default router;