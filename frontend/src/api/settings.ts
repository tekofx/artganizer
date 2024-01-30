import axios from "axios";
import { Settings } from "../../interfaces";

export async function handleEditSettings(
  settings: Settings
): Promise<Settings | undefined> {
  var result = await axios
    .put("http://localhost:3000/api" + "/settings/", settings)
    .then((response) => {
      return response.data;
    })
    .catch(() => {
      return undefined;
    });
  return result;
}

export async function handleResetSettings(): Promise<Settings | undefined> {
  var result = await axios
    .delete("http://localhost:3000/api" + "/settings/")
    .then((response) => {
      return response.data;
    })
    .catch(() => {
      return undefined;
    });
  return result;
}
