import axios from "axios";
import { Artist } from "../../interfaces";
export async function handleCreateArtist(
  artist: Artist
): Promise<Artist | undefined> {
  const formData = new FormData();
  formData.append("name", artist.name);
  formData.append("description", artist.description);
  formData.append("image", artist.image);
  formData.append("socials", JSON.stringify(artist.socials));

  var result = await axios
    .post("http://localhost:3000/api" + "/artists", formData)
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
    console.log("image");
  }
  formData.append("name", artist.name);
  formData.append("description", artist.description);
  formData.append("socials", JSON.stringify(artist.socials));

  var result = await axios
    .put("http://localhost:3000/api" + "/artists/" + artist.id, formData)
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
    .delete("http://localhost:3000/api" + "/artists/" + artist.id)
    .then(() => {
      return true;
    })
    .catch(() => {
      return undefined;
    });
  return result;
}
