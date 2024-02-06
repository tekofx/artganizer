import axios from "axios";
import { Character } from "../../interfaces";
import AxiosOptions from "./AxiosOptions";

export async function handleGetCharacters(): Promise<Character[]> {
  var result = await axios
    .get("/api/characters", AxiosOptions)
    .then((response) => {
      return response.data;
    })
    .catch(() => {
      return [];
    });
  return result;
}

export async function handleCreateCharacter(
  character: Character
): Promise<Character | undefined> {
  const formData = new FormData();
  formData.append("name", character.name);
  formData.append("description", character.description);
  formData.append("image", character.image);

  var result = await axios
    .post("/api/characters", formData, AxiosOptions)
    .then((response) => {
      return response.data;
    })
    .catch(() => {
      return undefined;
    });
  return result;
}

export async function handleEditCharacter(
  character: Character
): Promise<Character | undefined> {
  const formData = new FormData();
  if (character.image) {
    formData.append("image", character.image);
  }
  formData.append("name", character.name);
  formData.append("description", character.description);

  var result = await axios
    .put(`/api/characters/${character.id}`, formData, AxiosOptions)
    .then((response) => {
      return response.data;
    })
    .catch(() => {
      return undefined;
    });
  return result;
}

export async function handleRemoveCharacter(
  character: Character
): Promise<boolean | undefined> {
  var result = await axios
    .delete(`/api/characters/${character.id}`, AxiosOptions)
    .then(() => {
      return true;
    })
    .catch(() => {
      return undefined;
    });
  return result;
}
