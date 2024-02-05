import express from "express";

const router = express.Router();
router.post("/", (req, res) => {
  console.log(req.session);
  res.send("Hello world");
});

export default router;
