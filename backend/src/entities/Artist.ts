import { Column, Entity, OneToMany, PrimaryGeneratedColumn } from "typeorm";
import { ArtistData } from "../../../common/entitiesData";
import { Social } from "./Social";
import { Submission } from "./Submission";
@Entity()
export class Artist {
  @PrimaryGeneratedColumn()
  id: number;

  @Column({ length: ArtistData.nameLenght })
  name: string;

  @Column({ length: ArtistData.descriptionLenght, default: "" })
  description: string;

  @OneToMany(() => Social, (social) => social.artist, { onDelete: "CASCADE" })
  socials: Social[];

  @OneToMany(() => Submission, (submission) => submission.artist, {
    onDelete: "SET NULL",
  })
  submissions: Submission[];

  @Column({ default: "" })
  image: string;
}
