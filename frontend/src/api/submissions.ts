import axios from "axios";
import { Submission } from "../../interfaces";

export async function handleCreateSubmission(
  submission: Submission
): Promise<Submission | undefined> {
  const formData = new FormData();
  formData.append("image", submission.image);
  formData.append("title", submission.title);
  formData.append("description", submission.description);
  formData.append("rating", submission.rating.toString());
  if (submission.tags != undefined) {
    submission.tags.forEach((tag) => {
      formData.append("tags", tag.id.toString());
    });
  }
  if (submission.artist != undefined) {
    formData.append("artist", submission.artist.id.toString());
  }
  if (submission.characters != undefined) {
    submission.characters.forEach((character) => {
      formData.append("characters", character.id.toString());
    });
  }

  // Create submission
  var status = await axios
    .post("http://localhost:3000/api" + `/submissions`, formData)
    .then((response) => {
      return response.data;
    })
    .catch((error) => {
      console.log(error);
      return undefined;
    });

  return status;
}

export async function handleEditSubmission(
  submission: Submission
): Promise<Submission | undefined> {
  // Edit submission
  var status = await axios
    .put("http://localhost:3000/api" + `/submissions/${submission.id}`, {
      submission,
    })
    .then((response) => {
      return response.data;
    })
    .catch(() => {
      return undefined;
    });

  return status;
}

export async function handleRemoveSubmission(
  submission: Submission
): Promise<boolean | undefined> {
  // Remove submission
  var status = await axios
    .delete("http://localhost:3000/api" + `/submissions/${submission.id}`)
    .then(() => {
      return true;
    })
    .catch(() => {
      return undefined;
    });

  return status;
}
