import axios from "axios";
import { Settings } from "../../interfaces";
import AxiosOptions from "./AxiosOptions";

export async function handleGetSettings(): Promise<Settings | undefined> {
  var result = await axios
    .get("/api/settings/", AxiosOptions)
    .then((response) => {
      return response.data;
    })
    .catch(() => {
      return undefined;
    });
  return result;
}

export async function handleEditSettings(
  settings: Settings
): Promise<Settings | undefined> {
  var result = await axios
    .put("/api/settings/", settings, AxiosOptions)
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
    .delete("/api/settings/", AxiosOptions)
    .then((response) => {
      return response.data;
    })
    .catch(() => {
      return undefined;
    });
  return result;
}
