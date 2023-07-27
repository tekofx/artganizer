import { Entity, Column, PrimaryGeneratedColumn, OneToMany, OneToOne, JoinColumn, PrimaryColumn, ManyToOne, ManyToMany, JoinTable } from "typeorm";
import { Artist } from "./Artist";
import { Folder } from "./Folder";
import { Label } from "./Label";
import { Character } from "./Character";


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

    @ManyToMany(() => Folder)
    @JoinTable()
    folders: Folder[];

    @ManyToMany(() => Label)
    @JoinTable()
    labels: Label[];

    @ManyToMany(() => Character)
    @JoinTable()
    characters: Character[];

    // Image format
    @Column({ length: 10 })
    format: string;

    image: string;
}