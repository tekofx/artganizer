import {
  Stack,
  TextField,
  Grid,
  Typography,
  Button,
  IconButton,
  InputAdornment,
  Avatar,
  Dialog,
  DialogTitle,
  DialogActions,
  DialogContent,
} from "@mui/material";
import { useState, useContext } from "react";
import { DataContext } from "../../pages/_app";
import axios from "axios";
import Artist from "../../interfaces/Artist";
import LanguageIcon from "@mui/icons-material/Language";
import Social from "../../interfaces/Social";
import ClearIcon from "@mui/icons-material/Clear";
import LimitedTextField from "../LimitedTextField";
interface AlertMessage {
  message: string;
  severity: "success" | "error" | "info" | "warning";
}
const defaultArtist: Artist = {
  id: -1,
  name: "",
  description: "",
  socials: [],
  submissions: [],
  image: "/placeholder.jpg",
};

const defaultSocials: Social[] = [
  {
    name: "",
    url: "",
    favicon: "",
  },
];
function isValidUrl(string: string) {
  try {
    new URL(string);
    return true;
  } catch (_) {
    return false;
  }
}

interface Props {
  open: boolean;
  setOpen: React.Dispatch<React.SetStateAction<boolean>>;
  setOpenSnack: React.Dispatch<React.SetStateAction<boolean>>;
  setAlertMessage: React.Dispatch<React.SetStateAction<AlertMessage>>;
}

export default function ArtistForm(props: Props) {
  const [artist, setArtist] = useState<Artist>(defaultArtist);
  const [image, setImage] = useState<string>("/placeholder.jpg");
  const [socials, setSocials] = useState<Social[]>(defaultSocials);

  const { data, setData } = useContext(DataContext);

  function handleSocialURLChange(event: any, index: number) {
    const newSocials = [...socials];
    newSocials[index].url = event.target.value;
    var baseURL = "https://" + event.target.value.split("/")[0];

    if (isValidUrl(baseURL)) {
      newSocials[index].favicon = `${baseURL}/favicon.ico?`;
    }
    setSocials(newSocials);
  }

  function handleSocialNameChange(event: any, index: number) {
    const newSocials = [...socials];
    newSocials[index].name = event.target.value;
    setSocials(newSocials);
  }
  function onImageUpload(event: any) {
    console.log(event.target.files[0]);
    const newArtist = { ...artist };
    newArtist.image = event.target.files[0];
    setArtist(newArtist);
    setImage(URL.createObjectURL(event.target.files[0]));
    console.log(artist);
  }

  function addEmptySocial() {
    const newSocials = [...socials];
    newSocials.push({
      name: "",
      url: "",
      favicon: "",
    });
    setSocials(newSocials);
  }

  function removeSocial(index: number) {
    const newSocials = [...socials];
    newSocials.splice(index, 1);
    setSocials(newSocials);
  }

  async function postArtist(artist: Artist) {
    const formData = new FormData();
    formData.append("name", artist.name);
    formData.append("description", artist.description);
    formData.append("image", artist.image);
    formData.append("socials", JSON.stringify(socials));
    artist.socials = socials;

    await axios
      .post("http://localhost:3001/artists", formData)
      .then((response) => {
        var newData = { ...data };
        newData.artists.push(response.data);
        setData(newData);
        props.setAlertMessage({
          message: "Artist created",
          severity: "success",
        });
        console.log(response);
      })
      .catch((error) => {
        console.log(error);
        props.setAlertMessage({
          message: "Error creating artist",
          severity: "error",
        });
      })
      .finally(() => {
        props.setOpenSnack(true);
      });
  }

  return (
    <Dialog open={props.open} onClose={() => props.setOpen(false)}>
      <DialogTitle>Create Artist</DialogTitle>
      <DialogContent sx={{ p: 2 }}>
        <Grid container spacing={2}>
          <Grid item lg={4}>
            <input
              accept="image/*"
              id="contained-button-file"
              multiple
              type="file"
              hidden
              onChange={onImageUpload}
            />
            <label htmlFor="contained-button-file">
              <IconButton component="span">
                <Avatar src={image} sx={{ width: "8rem", height: "8rem" }} />
              </IconButton>
            </label>
          </Grid>
          <Grid item lg={8}>
            <Stack spacing={2}>
              <TextField
                label="Name"
                value={artist.name}
                onChange={(event) => {
                  setArtist((prevSubmission) => ({
                    ...prevSubmission,
                    name: event.target.value,
                  }));
                }}
              />
              <LimitedTextField
                label="Description"
                maxLength={200}
                multiline
                value={artist.description}
                onChange={(event: React.ChangeEvent<HTMLInputElement>) => {
                  setArtist((prevSubmission) => ({
                    ...prevSubmission,
                    description: event.target.value,
                  }));
                }}
              />
            </Stack>
          </Grid>

          <Grid item lg={12}>
            <Stack spacing={2}>
              <Typography>Socials</Typography>

              {socials.map((value, i) => (
                <Stack direction="row" alignItems="center" spacing={2} key={i}>
                  {value.favicon == "" ? (
                    <LanguageIcon />
                  ) : (
                    <img src={value.favicon} width="20px" />
                  )}
                  <TextField
                    label="Social Name"
                    value={value.name}
                    onChange={(event) => {
                      handleSocialNameChange(event, i);
                    }}
                  />
                  <TextField
                    label="URL"
                    value={value.url}
                    InputProps={{
                      startAdornment: (
                        <InputAdornment position="start">
                          https://
                        </InputAdornment>
                      ),
                    }}
                    onChange={(event) => {
                      handleSocialURLChange(event, i);
                    }}
                  />

                  <IconButton
                    aria-label="delete"
                    onClick={() => {
                      removeSocial(i);
                    }}
                  >
                    <ClearIcon />
                  </IconButton>
                </Stack>
              ))}
              <Button
                variant="contained"
                onClick={() => {
                  addEmptySocial();
                }}
              >
                Add Social
              </Button>
            </Stack>
          </Grid>
        </Grid>
      </DialogContent>
      <DialogActions>
        <Button
          variant="contained"
          onClick={() => {
            postArtist(artist);
            props.setOpen(false);
          }}
        >
          Create
        </Button>
        <Button onClick={() => props.setOpen(false)}>Cancel</Button>
      </DialogActions>
    </Dialog>
  );
}
