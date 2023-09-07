import { DataSource } from "typeorm";
import { Social, Submission, Artist, Tag, Character } from "./entities";
import { config } from "./config";
if (process.env.NODE_ENV !== "production") {
  require("dotenv").config();
}

export const AppDataSource = new DataSource({
  type: "mysql",
  host: config.MYSQL_HOST,
  port: 3306,
  username: config.MYSQL_USER,
  password: config.MYSQL_PASSWORD,
  database: config.MYSQL_DATABASE,
  entities: [Artist, Submission, Tag, Character, Social],
  synchronize: true,
  logging: false,
});

export const ArtistRepo = AppDataSource.manager.getRepository(Artist);
export const SubmissionRepo = AppDataSource.manager.getRepository(Submission);
export const TagRepo = AppDataSource.manager.getRepository(Tag);
export const CharacterRepo = AppDataSource.manager.getRepository(Character);
export const SocialRepo = AppDataSource.manager.getRepository(Social);
