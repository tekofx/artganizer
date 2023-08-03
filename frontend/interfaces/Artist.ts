import Submission from "./Submission";

export default interface Artist {
  id: number;
  name: string;
  description: string;
  socials: string;
  submissions: Submission[];
  image: string;
}
