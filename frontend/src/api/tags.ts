import axios from "axios";
import { Tag } from "../../interfaces";

export async function handleCreateTag(tag: Tag): Promise<Tag | undefined> {
  var result = await axios
    .post(process.env.API_URL + `/tags`, tag)
    .then((response) => {
      return response.data;
    })
    .catch(() => {
      return undefined;
    });

  return result;
}

export async function handleEditTag(tag: Tag): Promise<Tag | undefined> {
  var result = await axios
    .put(process.env.API_URL + `/tags/${tag.id}`, tag)
    .then((response) => {
      return response.data;
    })
    .catch(() => {
      return undefined;
    });

  return result;
}

export async function handleRemoveTag(tag: Tag): Promise<boolean | undefined> {
  var result = await axios
    .delete(process.env.API_URL + `/tags/${tag.id}`)
    .then((response) => {
      return response.data;
    })
    .catch(() => {
      return undefined;
    });

  return result;
}
