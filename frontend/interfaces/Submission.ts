import Tag from "./Tag";
import Folder from "./Folder";
import Character from "./Character";
import Artist from "./Artist";
export default interface Submission {
  id: number;
  title: string;
  description: string;
  date: Date;
  rating: number;
  width: number;
  height: number;
  artists: Artist[];
  folders: Folder[];
  tags: Tag[];
  characters: Character[];
  format: string;
  image: string;
}
