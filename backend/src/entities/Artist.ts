import {
  Entity,
  Column,
  PrimaryGeneratedColumn,
  OneToOne,
  JoinColumn,
  PrimaryColumn,
  ManyToMany,
  OneToMany,
} from "typeorm";
import { Submission } from "./Submission";

@Entity()
export class Artist {
  @PrimaryGeneratedColumn()
  id: number;

  @Column({ length: 20 })
  name: string;

  @Column({ length: 200, default: "" })
  description: string;

  @Column({ type: "json" })
  socials: {
    favicon: string;
    url: string;
    name: string;
  }[];

  @OneToMany(() => Submission, (submission) => submission.artist)
  submissions: Submission[];

  image: string;
}
