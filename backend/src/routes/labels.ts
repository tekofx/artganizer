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

router.get('/:id', async (req: Request, res: Response) => {
    var id = parseInt(req.params.id);
    const label = await LabelRepo.findOne({ where: { id: id } });
    res.send(label);
});



router.post('/', async (req: Request, res: Response) => {
    const { name } = req.body;
    const label = LabelRepo.create({ name });
    await LabelRepo.save(label);
    var labels = await LabelRepo.find();
    res.send(labels);
});

export default router;