import Social from "./Social";
import Submission from "./Submission";

export default interface Artist {
  id: number;
  name: string;
  description: string;
  socials: Social[];
  submissions: Submission[];
  image: string;
}
