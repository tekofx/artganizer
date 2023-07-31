import express, { Request, Response } from 'express';
import "reflect-metadata";
import { Submission } from '../entities/Submission';
import { TagRepo, SubmissionRepo } from '../typeorm.config';
import { Folder } from '../entities/Folder';

const router = express.Router();


router.get('/', async (req: Request, res: Response) => {
    const tags = await TagRepo.find();
    res.send(tags);
});

router.get('/:id', async (req: Request, res: Response) => {
    var id = parseInt(req.params.id);
    const label = await TagRepo.findOne({ where: { id: id } });
    res.send(label);
});



router.post('/', async (req: Request, res: Response) => {
    const { name, color } = req.body;
    const label = TagRepo.create({ name: name, color: color });
    await TagRepo.save(label);
    var tags = await TagRepo.find();
    res.send(tags);
});

router.put('/:id', async (req: Request, res: Response) => {
    var id = parseInt(req.params.id);
    const { name, color } = req.body;
    const label = await TagRepo.findOne({ where: { id: id } });
    if(label==null){
        return res.status(404).send("label not found");
    }
    await TagRepo.update(label, { name: name, color: color });
    
    // Return updated label
    const updatedLabel = await TagRepo.findOne({ where: { id: id } });
    res.send(updatedLabel);

});

export default router;