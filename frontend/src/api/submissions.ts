import axios from "axios";
import { Artist, Character, Submission, Tag } from "../../interfaces";

export async function handleCreateSubmission(
  submission: Submission,
  selectedTags: Tag[],
  selectedArtist: Artist | undefined,
  selectedCharacters: Character[]
): Promise<Submission | undefined> {
  const formData = new FormData();
  formData.append("image", submission.image);
  formData.append("title", submission.title);
  formData.append("description", submission.description);
  formData.append("rating", submission.rating.toString());
  if (selectedTags != undefined) {
    selectedTags.forEach((tag) => {
      formData.append("tags", tag.id.toString());
    });
  }
  if (selectedArtist != undefined) {
    formData.append("artist", selectedArtist.id.toString());
  }
  if (selectedCharacters != undefined) {
    selectedCharacters.forEach((character) => {
      formData.append("characters", character.id.toString());
    });
  }

  // Create submission
  var status = await axios
    .post(process.env.API_URL + `/submissions`, formData)
    .then((response) => {
      return response.data;
    })
    .catch(() => {
      return undefined;
    });

  return status;
}
