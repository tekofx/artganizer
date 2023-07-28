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
    const { name, color } = req.body;
    const label = LabelRepo.create({ name: name, color: color });
    await LabelRepo.save(label);
    var labels = await LabelRepo.find();
    res.send(labels);
});

router.put('/:id', async (req: Request, res: Response) => {
    var id = parseInt(req.params.id);
    const { name, color } = req.body;
    const label = await LabelRepo.findOne({ where: { id: id } });
    if(label==null){
        return res.status(404).send("label not found");
    }
    await LabelRepo.update(label, { name: name, color: color });
    
    // Return updated label
    const updatedLabel = await LabelRepo.findOne({ where: { id: id } });
    res.send(updatedLabel);

});

export default router;