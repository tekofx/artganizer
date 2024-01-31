import Artist from "./Artist";
import Character from "./Character";
import Tag from "./Tag";
export default interface Filters {
  rating: number;
  tags: Tag[];
  artists: Artist[];
  title: string;
  characters: Character[];
  color: string;
}
