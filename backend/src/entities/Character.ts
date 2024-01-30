import { Column, Entity, PrimaryGeneratedColumn } from "typeorm";
import { CharacterData } from "../../../common/entitiesData";

@Entity()
export class Character {
  @PrimaryGeneratedColumn()
  id: number;

  @Column({ length: CharacterData.nameLenght })
  name: string;

  @Column({ length: CharacterData.descriptionLenght, default: "" })
  description: string;

  @Column({ default: "" })
  image: string;
}
