import axios from "axios";
import { Tag } from "../../interfaces";
import AxiosOptions from "./AxiosOptions";

export async function handleGetTags(): Promise<Tag[]> {
  var result = await axios
    .get("/api/tags", AxiosOptions)
    .then((response) => {
      return response.data;
    })
    .catch(() => {
      return [];
    });

  return result;
}

export async function handleCreateTag(tag: Tag): Promise<Tag | undefined> {
  var result = await axios
    .post("/api/tags", tag, AxiosOptions)
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
    .put(`/api/tags/${tag.id}`, tag, AxiosOptions)
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
    .delete(`/api/tags/${tag.id}`, AxiosOptions)
    .then((response) => {
      return response.data;
    })
    .catch(() => {
      return undefined;
    });

  return result;
}
