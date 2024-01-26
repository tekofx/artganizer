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
    .post(process.env.API_URL + "/artists", formData)
    .then((response) => {
      return response.data;
    })
    .catch(() => {
      return undefined;
    });
  return result;
}
