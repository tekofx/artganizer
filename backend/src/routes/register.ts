import bcrypt from "bcrypt";
import express from "express";
import { UserRepo } from "../typeorm.config";

const router = express.Router();
router.post("/", async (req, res) => {
  var { username, password } = req.body;
  const existsUser = await UserRepo.findOne({ where: { username: username } });
  if (existsUser) {
    return res.status(400).send("User already exists");
  }

  bcrypt.hash(password, 10).then(async (hashedPassword) => {
    // Store hash in your password DB.
    console.log("createing user...");
    await UserRepo.save({
      username: username,
      password: hashedPassword,
    })
      .catch((e) => {
        console.log(e);
        return res.status(500).send("Error creating user");
      })
      .then(() => {
        return res.send("User created");
      });
  });
});

export default router;
