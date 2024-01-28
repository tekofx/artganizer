import Artist from "./Artist";
import Character from "./Character";
import Tag from "./Tag";
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
  filename: string;
  original_image: string;
  thumbnail: string;
  image: string;
  colors: string[];
  size: number;
}
