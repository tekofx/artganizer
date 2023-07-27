import express, { Request, Response } from 'express';
import "reflect-metadata";
import { Submission } from '../entities/Submission';
import { FolderRepo, SubmissionRepo } from '../typeorm.config';
import { Folder } from '../entities/Folder';

const router = express.Router();


router.get('/', async (req: Request, res: Response) => {
    const folders = await FolderRepo.find();
    res.send(folders);
});


router.post('/', async (req: Request, res: Response) => {
    const { name, description } = req.body;
    const folder = FolderRepo.create({ name, description });
    await FolderRepo.save(folder);
    var folders = await FolderRepo.find();
    res.send(folders);
});

export default router;