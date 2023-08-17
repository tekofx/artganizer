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
} from "@mui/material";
import { useState, useContext } from "react";
import { DataContext } from "../../pages/_app";
import axios from "axios";
import Artist from "../../interfaces/Artist";
interface AlertMessage {
  message: string;
  severity: "success" | "error" | "info" | "warning";
}
const defaultArtist: Artist = {
  id: -1,
  name: "",
  description: "",
  socials: "",
  submissions: [],
  image: "/placeholder.jpg",
};

const defaultAlertMessage: AlertMessage = {
  message: "Artist created",
  severity: "success",
};

export default function ArtistForm() {
  const [artist, setArtist] = useState<Artist>(defaultArtist);
  const [open, setOpen] = useState<boolean>(false);
  const [image, setImage] = useState<string>("/placeholder.jpg");
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
  function onImageUpload(event: any) {
    console.log(event.target.files[0]);
    const newArtist = { ...artist };
    newArtist.image = event.target.files[0];
    setArtist(newArtist);
    setImage(URL.createObjectURL(event.target.files[0]));
    console.log(artist);
  }

  async function postArtist(artist: Artist) {
    const formData = new FormData();
    formData.append("name", artist.name);
    formData.append("description", artist.description);
    formData.append("image", artist.image);

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
        <Grid item lg={4}>
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
              <TextField
                label="Description"
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
