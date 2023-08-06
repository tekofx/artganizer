import Tag from "./Tag";
import Folder from "./Folder";
import Character from "./Character";
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
  characters: Character[];
  format: string;
  image: string;
}
