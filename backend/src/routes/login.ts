import bcrypt from "bcrypt";
import express from "express";
import jwt from "jsonwebtoken";
import { UserRepo } from "../typeorm.config";
const router = express.Router();
router.post("/", async (req, res) => {
  await UserRepo.findOne({ where: { username: req.body.username } })
    .then((user) => {
      bcrypt.compare(req.body.password, user.password).then((passwordMatches) => {
       if (!passwordMatches){
        return res.status(500).send("Password does not match");
       }

       const token=jwt.sign({username:user.username},process.env.BACKEND_SECRET_KEY,{expiration:60*60});

      }


    })
    .catch((e) => {
      res.status(500).send("User not found");
    });

  console.log(req.session);
  res.send("Hello world");
});

export default router;
