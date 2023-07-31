import Tag from "./Tag";
import Folder from "./Folder";
export default interface Submission {
    id: number;
    title: string;
    description: string;
    date: Date;
    rating: number;
    width: number;
    height: number;
    artist: string;
    folders: Folder[];
    tags: Tag[];
    characters: number[];
    format: string;
    image: string;
}