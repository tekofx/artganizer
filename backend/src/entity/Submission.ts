import { Entity, Column, PrimaryGeneratedColumn, OneToMany, OneToOne, JoinColumn, PrimaryColumn, ManyToOne, ManyToMany } from "typeorm";
import { Artist } from "./Artist";

@Entity()
export class Submission {
    @PrimaryGeneratedColumn()
    /* @OneToMany(() => User, user => user.favorites) */
    id: number;



    @Column({ length: 50 })
    title: string;

    @Column({ length: 500 })
    description: string;

    @Column({ type: "datetime", default: () => "CURRENT_TIMESTAMP" })
    date: Date;

    @Column({ type: "tinyint", default: 0 })
    rating: number;

    @Column()
    width: number;

    @Column()
    height: number;

    @ManyToOne(() => Artist, (artist) => artist.submissions)
    artist: Artist;







}