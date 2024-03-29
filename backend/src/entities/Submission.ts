import {
  Column,
  Entity,
  JoinTable,
  ManyToMany,
  ManyToOne,
  PrimaryGeneratedColumn,
} from "typeorm";
import { SubmissionData } from "../../../common/entitiesData";
import { Artist } from "./Artist";
import { Character } from "./Character";
import { Tag } from "./Tag";

@Entity()
export class Submission {
  @PrimaryGeneratedColumn()
  /* @OneToMany(() => User, user => user.favorites) */
  id: number;

  @Column({ length: SubmissionData.titleLenght, default: "Untitled" })
  title: string;

  @Column({ length: SubmissionData.descriptionLenght, default: "" })
  description: string;

  @Column({ type: "datetime", default: () => "CURRENT_TIMESTAMP" })
  date: Date;

  @Column({ type: "tinyint", default: 0 })
  rating: number;

  @Column()
  width: number;

  @Column()
  height: number;

  @ManyToOne(() => Artist, (artist) => artist.submissions, {
    onDelete: "SET NULL",
  })
  artist: Artist;

  @ManyToMany(() => Tag)
  @JoinTable()
  tags: Tag[];

  @ManyToMany(() => Character)
  @JoinTable()
  characters: Character[];

  // Image format
  @Column({ length: 10 })
  format: string;

  // Each color delimited by a comma (,)
  @Column({ type: "simple-array" })
  colors: string[];

  // File size in bytes
  @Column()
  size: number;

  // Name of the original submission file
  @Column()
  filename: string;

  // Original image
  @Column()
  original_image: string;

  // Thumbnail image
  @Column()
  thumbnail: string;

  // Display image
  @Column()
  image: string;
}
