import { Column, Entity, PrimaryGeneratedColumn } from "typeorm";
import { TagData } from "../../../common/entitiesData";

@Entity()
export class Tag {
  @PrimaryGeneratedColumn()
  id: number;

  @Column({ length: TagData.nameLenght })
  name: string;

  @Column({ length: 7, default: "#ffffff" })
  color: string;

  submissionCount: number;
}
