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
