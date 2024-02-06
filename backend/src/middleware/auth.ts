import jwt from "jsonwebtoken";
import { config } from "../config";

export default function auth(req: any, res: any, next: any) {
  try {
    const token = req.headers.authorization.split(" ")[1];
    const decoded = jwt.verify(token, config.BACKEND_SECRET_KEY);
    req.userData = decoded;
    next();
  } catch (error) {
    return res.status(401).send("Auth failed");
  }
}
