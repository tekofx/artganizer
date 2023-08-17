import Submission from "./Submission";

export default interface Artist {
  id: number;
  name: string;
  description: string;
  socials: {
    favicon: string;
    url: string;
    name: string;
  }[];
  submissions: Submission[];
  image: string;
}
