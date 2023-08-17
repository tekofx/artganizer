import {
  FormControl,
  Stack,
  TextField,
  Paper,
  Grid,
  Typography,
  Button,
  Snackbar,
  Alert,
  IconButton,
  InputAdornment,
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

const defaultAlertMessage: AlertMessage = {
  message: "Artist created",
  severity: "success",
};

const defaultSocials: Social[] = [
  {
    name: "",
    url: "",
    favicon: "",
  },
];
function isValidUrl(string) {
  try {
    new URL(string);
    return true;
  } catch (_) {
    return false;
  }
}

export default function ArtistForm() {
  const [artist, setArtist] = useState<Artist>(defaultArtist);
  const [open, setOpen] = useState<boolean>(false);
  const [image, setImage] = useState<string>("/placeholder.jpg");
  const [socials, setSocials] = useState<Social[]>(defaultSocials);

  const { data, setData } = useContext(DataContext);

  const [alertMessage, setAlertMessage] =
    useState<AlertMessage>(defaultAlertMessage);

  const handleClose = (
    event?: React.SyntheticEvent | Event,
    reason?: string
  ) => {
    if (reason === "clickaway") {
      return;
    }

    setOpen(false);
  };

  function handleSocialURLChange(event: any, index: number) {
    const newSocials = [...socials];
    newSocials[index].url = event.target.value;
    setSocials(newSocials);
    var url = "https://" + event.target.value;
    if (isValidUrl(url)) {
      newSocials[index].favicon = `${url}/favicon.ico?`;
    }
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

    const response = await axios.post(
      `http://localhost:3001/artists`,
      formData
    );
    if (response.status != 200) {
      setAlertMessage({ message: "Error creating artist", severity: "error" });
    }
    var newData = { ...data };
    newData.artists.push(artist);
    setData(newData);
    setOpen(true);
  }

  return (
    <Paper sx={{ p: 2 }}>
      <Grid container spacing={2}>
        <Grid item lg={12}>
          <Typography>Create Artist</Typography>
        </Grid>
        <Grid item>
          <FormControl fullWidth>
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
                onChange={(event) => {
                  setArtist((prevSubmission) => ({
                    ...prevSubmission,
                    description: event.target.value,
                  }));
                }}
              />
              <Button variant="contained" onClick={() => postArtist(artist)}>
                Create
              </Button>
              <Typography>Socials</Typography>

              {socials.map((value, i) => (
                <Stack direction="row" alignItems="center" spacing={2} key={i}>
                  {value.favicon == "" ? (
                    <LanguageIcon />
                  ) : (
                    <img src={value.favicon} width="20px" />
                  )}

                  <TextField
                    label="Social URL"
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
                  <TextField
                    label="Link"
                    value={value.name}
                    onChange={(event) => {
                      handleSocialNameChange(event, i);
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
          </FormControl>
        </Grid>
        <Grid item lg={4}>
          <img src={image} width="100%" />
          <Button variant="contained" component="label">
            Upload File
            <input type="file" hidden onChange={onImageUpload} />
          </Button>
        </Grid>
      </Grid>

      <Snackbar
        open={open}
        autoHideDuration={6000}
        onClose={handleClose}
        anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
      >
        <Alert
          onClose={handleClose}
          severity={alertMessage.severity}
          sx={{ width: "100%" }}
        >
          {alertMessage.message}
        </Alert>
      </Snackbar>
    </Paper>
  );
}
