import { DataSource } from "typeorm";
import { config } from "./config";
import { Artist, Character, Social, Submission, Tag, User } from "./entities";
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
  entities: [Artist, Submission, Tag, Character, Social, User],
  synchronize: true,
  logging: false,
});

export const ArtistRepo = AppDataSource.manager.getRepository(Artist);
export const SubmissionRepo = AppDataSource.manager.getRepository(Submission);
export const TagRepo = AppDataSource.manager.getRepository(Tag);
export const CharacterRepo = AppDataSource.manager.getRepository(Character);
export const SocialRepo = AppDataSource.manager.getRepository(Social);
export const UserRepo = AppDataSource.manager.getRepository(User);
