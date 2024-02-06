import axios from "axios";
import { Artist } from "../../interfaces";
import AxiosOptions from "./AxiosOptions";

export async function handleGetArtists(): Promise<Artist[]> {
  var result = await axios
    .get("/api/artists", AxiosOptions)
    .then((response) => {
      return response.data;
    })
    .catch(() => {
      return [];
    });
  return result;
}

export async function handleCreateArtist(
  artist: Artist
): Promise<Artist | undefined> {
  const formData = new FormData();
  formData.append("name", artist.name);
  formData.append("description", artist.description);
  formData.append("image", artist.image);
  formData.append("socials", JSON.stringify(artist.socials));

  var result = await axios
    .post("/api/artists", formData, AxiosOptions)
    .then((response) => {
      return response.data;
    })
    .catch(() => {
      return undefined;
    });
  return result;
}

export async function handleEditArtist(
  artist: Artist
): Promise<Artist | undefined> {
  const formData = new FormData();
  if (artist.image) {
    formData.append("image", artist.image);
  }
  formData.append("name", artist.name);
  formData.append("description", artist.description);
  formData.append("socials", JSON.stringify(artist.socials));

  var result = await axios
    .put(`/api/artists/${artist.id}`, formData, AxiosOptions)
    .then((response) => {
      return response.data;
    })
    .catch(() => {
      return undefined;
    });
  return result;
}

export async function handleRemoveArtist(
  artist: Artist
): Promise<boolean | undefined> {
  var result = await axios
    .delete(`/api/artists/${artist.id}`, AxiosOptions)
    .then(() => {
      return true;
    })
    .catch(() => {
      return undefined;
    });
  return result;
}
