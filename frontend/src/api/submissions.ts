import axios, { AxiosError } from "axios";
import { Submission } from "../../interfaces";
import ApiError from "./ApiError";

export async function handleCreateSubmission(
  submission: Submission
): Promise<Submission> {
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

  return await axios
    .post("/api/submissions", formData)
    .then((response) => {
      return response.data;
    })
    .catch((error: AxiosError) => {
      var message: string = "error: " + error.response?.data;
      throw new ApiError(message);
    });
}

export async function handleEditSubmission(
  submission: Submission
): Promise<Submission | undefined> {
  // Edit submission
  var status = await axios
    .put(`/api/submissions/${submission.id}`, {
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
    .delete(`/api/submissions/${submission.id}`)
    .then(() => {
      return true;
    })
    .catch(() => {
      return undefined;
    });

  return status;
}
