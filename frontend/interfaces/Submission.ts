import Tag from "./Tag";
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
  artist: Artist | undefined;
  tags: Tag[];
  characters: Character[];
  format: string;
  image: string;
  filename: string;
  colors: string[];
  size: number;
}
