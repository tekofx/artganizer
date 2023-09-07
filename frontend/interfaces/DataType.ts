import Tag from "./Tag";
import Submission from "./Submission";
import Filters from "./Filters";
import Artist from "./Artist";
import Character from "./Character";
import Settings from "./Settings";

export default interface DataType {
  tags: Tag[];
  submissions: Submission[];
  filters: Filters;
  artists: Artist[];
  characters: Character[];
  settings: Settings;
}
