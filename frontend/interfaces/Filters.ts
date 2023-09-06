import Tag from "./Tag";
import Folder from "./Folder";
import Artist from "./Artist";
import Character from "./Character";
export default interface Filters {
  rating: number;
  tags: Tag[];
  folders: Folder[];
  artist: Artist | undefined;
  title: string;
  characters: Character[];
  color: string;
}
