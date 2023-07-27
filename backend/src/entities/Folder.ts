import { Entity, Column, PrimaryGeneratedColumn, OneToOne, JoinColumn, PrimaryColumn, ManyToMany, OneToMany } from "typeorm";
import { Submission } from "./Submission";

@Entity()
export class Folder {
    @PrimaryGeneratedColumn()
    id: number;

    @Column({ length: 20 })
    name: string;

    @Column({ length: 200, default: "" })
    description: string;

    @OneToMany(() => Submission, submission => submission.artist)
    submissions: Submission[];

}