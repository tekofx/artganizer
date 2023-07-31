import Tag from "./Tag";

export default interface Submission {
    id: number;
    title: string;
    description: string;
    date: Date;
    rating: number;
    width: number;
    height: number;
    artist: string;
    folders: number[];
    tags: Tag[];
    characters: number[];
    format: string;
    image: string;
}