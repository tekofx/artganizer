import { Entity, Column, PrimaryGeneratedColumn } from "typeorm";

@Entity()
export class Label {
    @PrimaryGeneratedColumn()
    id: number;

    @Column({ length: 20 })
    name: string;

    @Column({ length: 7, default: "#ffffff" })
    color: string;



}