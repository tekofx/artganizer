import axios from "axios";
import { Character } from "../../interfaces";

export async function handleCreateCharacter(
  character: Character
): Promise<Character | undefined> {
  const formData = new FormData();
  formData.append("name", character.name);
  formData.append("description", character.description);
  formData.append("image", character.image);

  var result = await axios
    .post(process.env.API_URL + "/characters", formData)
    .then((response) => {
      return response.data;
    })
    .catch(() => {
      return undefined;
    });
  return result;
}
