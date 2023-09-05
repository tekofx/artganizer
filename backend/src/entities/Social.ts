import { Entity, Column, PrimaryGeneratedColumn, ManyToOne } from "typeorm";
import { Artist } from "./Artist";

@Entity()
export class Social {
  @PrimaryGeneratedColumn()
  id: number;

  @Column()
  url: string;

  @Column()
  name: string;

  @ManyToOne(() => Artist, (artist) => artist.socials)
  artist: Artist;
}
