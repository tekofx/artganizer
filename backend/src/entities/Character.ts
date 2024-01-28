import { Column, Entity, PrimaryGeneratedColumn } from "typeorm";

@Entity()
export class Character {
  @PrimaryGeneratedColumn()
  id: number;

  @Column({ length: 20 })
  name: string;

  @Column({ length: 200, default: "" })
  description: string;

  @Column({ default: "" })
  image: string;
}
