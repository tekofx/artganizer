import express, { NextFunction, Request, Response } from 'express';


export default function submissionPostValidation(req: Request, res: Response, next: NextFunction) {
    var { title, description, keywords, category, species, theme, gender } = req.body;
    console.log(req);
    var file = req.file;
    try {
        // Check fields
        if (!title || !description || !keywords || !category || !species || !theme || !gender) {
            throw new Error("Missing fields");
        }

        // Check file
        if (file == null) {
            throw new Error("File not provideddd");
        }
        if (file.mimetype != "image/jpeg" && file.mimetype != "image/png") {
            throw new Error("File must be an image");
        }
        next();

    } catch (error: unknown) {
        if (error instanceof Error) {
            res.status(400).send(error.message);
            return;
        }
    }

}