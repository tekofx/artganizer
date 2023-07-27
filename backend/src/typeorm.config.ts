import { DataSource } from 'typeorm';
import { Artist } from './entities/Artist';
import { Submission } from './entities/Submission';
import { Folder } from './entities/Folder';

if (process.env.NODE_ENV !== 'production') {
    require('dotenv').config();
    console.log(process.env.NODE_ENV);
    console.log(process.env.FOO);

}

export const AppDataSource = new DataSource({
    type: "mysql",
    host: process.env.MYSQL_HOST,
    port: 3306,
    username: process.env.MYSQL_USER,
    password: process.env.MYSQL_PASSWORD,
    database: process.env.MYSQL_DATABASE,
    entities: [Artist, Submission, Folder],
    synchronize: true,
    logging: false,
});

export const ArtistRepo = AppDataSource.manager.getRepository(Artist);
export const SubmissionRepo = AppDataSource.manager.getRepository(Submission);
export const FolderRepo = AppDataSource.manager.getRepository(Folder);