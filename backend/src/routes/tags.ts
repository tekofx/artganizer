import express, { Request, Response } from "express";
import "reflect-metadata";
import { Submission } from "../entities/Submission";
import { TagRepo, SubmissionRepo } from "../typeorm.config";
import { Folder } from "../entities/Folder";

const router = express.Router();

router.get("/", async (req: Request, res: Response) => {
  //const tags = await TagRepo.find();
  const tags = await TagRepo.createQueryBuilder("tag")
    .select([
      "tag.id AS id",
      "tag.name AS name",
      "tag.color AS color",
      "(SELECT COUNT(*) FROM submission_tags_tag WHERE submission_tags_tag.tagId = tag.id) AS submissionCount",
    ])
    .getRawMany();

  tags.forEach((tag) => {
    tag.submissionCount = parseInt(tag.submissionCount);
  });

  res.send(tags);
});

router.get("/:id", async (req: Request, res: Response) => {
  var id = parseInt(req.params.id);
  const label = await TagRepo.findOne({ where: { id: id } });
  res.send(label);
});

router.post("/", async (req: Request, res: Response) => {
  const { name, color } = req.body;
  const label = TagRepo.create({ name: name, color: color });
  const tag = await TagRepo.save(label);
  tag.submissionCount = 0;
  res.send(tag);
});

router.put("/:id", async (req: Request, res: Response) => {
  var id = parseInt(req.params.id);
  const { name, color } = req.body;
  const label = await TagRepo.findOne({ where: { id: id } });
  if (label == null) {
    return res.status(404).send("label not found");
  }
  await TagRepo.update(label, { name: name, color: color });

  // Return updated label
  const updatedLabel = await TagRepo.findOne({ where: { id: id } });
  res.send(updatedLabel);
});

router.delete("/:id", async (req: Request, res: Response) => {
  var id = parseInt(req.params.id);
  const tag = await TagRepo.findOne({ where: { id: id } });
  if (tag == null) {
    return res.status(404).send("tag not found");
  }
  await TagRepo.delete(tag);
  res.send("tag deleted");
});

export default router;
