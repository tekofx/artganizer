import bcrypt from "bcrypt";
import express from "express";
import { UserRepo } from "../typeorm.config";

const router = express.Router();
router.post("/", async (req, res) => {
  try {
    var { username, password } = req.body;
    bcrypt.hash(password, 10, async function (err, hash) {
      // Store hash in your password DB.
      await UserRepo.save({ username: username, password: hash });
    });
    return res.send("User created successfully!");
  } catch (e) {
    return res.status(500).send("Error creating user");
  }
});

export default router;
