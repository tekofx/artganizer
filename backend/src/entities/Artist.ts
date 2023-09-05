import { Entity, Column, PrimaryGeneratedColumn, OneToMany } from "typeorm";
import { Submission } from "./Submission";
import { Social } from "./Social";

@Entity()
export class Artist {
  @PrimaryGeneratedColumn()
  id: number;

  @Column({ length: 20 })
  name: string;

  @Column({ length: 200, default: "" })
  description: string;

  @OneToMany(() => Social, (social) => social.artist, { onDelete: "CASCADE" })
  socials: Social[];

  @OneToMany(() => Submission, (submission) => submission.artist)
  submissions: Submission[];

  image: string;
}
