import { Photo } from "react-photo-album";
import Submission from "./Submission";

export default interface SubmissionPhoto extends Photo {
  submission: Submission;
}
