import {
  Stack,
  TextField,
  Grid,
  Button,
  IconButton,
  Avatar,
  Dialog,
  DialogTitle,
  DialogActions,
  DialogContent,
} from "@mui/material";
import { useState, useContext } from "react";
import { DataContext } from "../../../pages/_app";
import axios from "axios";
import Artist from "../../../interfaces/Artist";
import LimitedTextField from "../../LimitedTextField";
import Socials from "./Socials";
import ProgressButton from "../ProgressButon";
import Snack from "../../Snack";
import { AlertMessage } from "../../../interfaces";

const defaultArtist: Artist = {
  id: -1,
  name: "",
  description: "",
  socials: [],
  submissions: [],
  image: "/placeholder.jpg",
};

interface Props {
  open: boolean;
  setOpen: React.Dispatch<React.SetStateAction<boolean>>;
}

export default function ArtistForm({ open, setOpen }: Props) {
  const [artist, setArtist] = useState<Artist>(defaultArtist);
  const [image, setImage] = useState<string>("/placeholder.jpg");
  const [loading, setLoading] = useState<boolean>(false);
  const [openSnack, setOpenSnack] = useState<boolean>(false);
  const [alertMessage, setAlertMessage] = useState<AlertMessage>({
    message: "Submission created",
    severity: "success",
  });

  const { data, setData } = useContext(DataContext);

  function onImageUpload(event: any) {
    const newArtist = { ...artist };
    newArtist.image = event.target.files[0];
    setArtist(newArtist);
    setImage(URL.createObjectURL(event.target.files[0]));
  }

  async function postArtist() {
    setLoading(true);
    const formData = new FormData();
    formData.append("name", artist.name);
    formData.append("description", artist.description);
    formData.append("image", artist.image);
    formData.append("socials", JSON.stringify(artist.socials));

    await axios
      .post(process.env.API_URL + "/artists", formData)
      .then((response) => {
        var newData = { ...data };
        newData.artists.push(response.data);
        setData(newData);
        setAlertMessage({
          message: "Artist created",
          severity: "success",
        });
      })
      .catch((error) => {
        console.log(error);
        setAlertMessage({
          message: "Error creating artist",
          severity: "error",
        });
      })
      .finally(() => {
        setLoading(false);
        setOpen(false);
        setImage("/placeholder.jpg");
        setOpenSnack(true);
        setArtist(defaultArtist);
      });
  }

  function onCancel() {
    setOpen(false);
    setImage("/placeholder.jpg");
    setArtist(defaultArtist);
  }

  return (
    <>
      <Dialog open={open} onClose={() => setOpen(false)}>
        <DialogTitle>Create Artist</DialogTitle>
        <DialogContent sx={{ p: 2 }}>
          <Grid container spacing={2}>
            <Grid item lg={4}>
              <input
                accept="image/*"
                id="artist-form-image"
                multiple
                type="file"
                hidden
                onChange={onImageUpload}
              />
              <label htmlFor="artist-form-image">
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
              <Socials artist={artist} setArtist={setArtist} />
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <ProgressButton
            loading={loading}
            disabled={artist.name == ""}
            onClick={postArtist}
            text="Create"
          />
          <Button disabled={loading} onClick={onCancel}>
            Cancel
          </Button>
        </DialogActions>
      </Dialog>
      <Snack
        open={openSnack}
        setOpen={setOpenSnack}
        alertMessage={alertMessage}
      />
    </>
  );
}
