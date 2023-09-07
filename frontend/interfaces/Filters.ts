import Tag from "./Tag";
import Artist from "./Artist";
import Character from "./Character";
export default interface Filters {
  rating: number;
  tags: Tag[];
  artist: Artist | undefined;
  title: string;
  characters: Character[];
  color: string;
}
