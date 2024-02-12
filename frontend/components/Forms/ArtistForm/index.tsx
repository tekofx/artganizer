import {
  Avatar,
  Box,
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Grid,
  IconButton,
  Stack
} from "@mui/material";
import { useState } from "react";
import { ArtistData } from "../../../../common/entitiesData";
import { AlertMessage } from "../../../interfaces";
import Artist from "../../../interfaces/Artist";
import { useAppContext } from "../../../pages/_app";
import LimitedTextField from "../../LimitedTextField";
import Snack from "../../Snack";
import ProgressButton from "../ProgressButon";
import Socials from "./Socials";
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

  const { createArtist, isMobile } = useAppContext();

  function onImageUpload(event: any) {
    const newArtist = { ...artist };
    newArtist.image = event.target.files[0];
    setArtist(newArtist);
    setImage(URL.createObjectURL(event.target.files[0]));
  }

  async function postArtist() {
    setLoading(true);
    const result = await createArtist(artist);
    if (result != undefined) {
      setAlertMessage({
        message: "Artist created",
        severity: "success",
      });
    } else {
      setAlertMessage({
        message: "Error creating artist",
        severity: "error",
      });
    }

    setLoading(false);
    setOpen(false);
    setImage("/placeholder.jpg");
    setOpenSnack(true);
    setArtist(defaultArtist);
  }

  function onCancel() {
    setOpen(false);
    setImage("/placeholder.jpg");
    setArtist(defaultArtist);
  }

  return (
    <>
      <Dialog open={open} onClose={() => setOpen(false)} fullScreen={isMobile ? true : false} sx={{ zIndex: 600 }}>
        <DialogTitle>Create Artist</DialogTitle>
        <DialogContent sx={{ p: 2 }}>
          <Grid container spacing={2} >
            <Grid item xs={12} lg={4}>
              <input
                accept="image/png, image/jpeg"
                id="artist-form-image"
                multiple
                type="file"
                hidden
                onChange={onImageUpload}
              />
              <Box display="flex" justifyContent="center" alignItems="center">
                <label htmlFor="artist-form-image">
                  <IconButton component="span">
                    <Avatar src={image} sx={{ width: "20vh", height: "20vh" }} />
                  </IconButton>
                </label>
              </Box>
            </Grid>
            <Grid item xs={12} lg={8}>
              <Stack spacing={2} sx={{ paddingTop: 1 }}>
                <LimitedTextField
                  label="Name"
                  multiline={false}
                  maxLength={ArtistData.nameLenght}
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
                  maxLength={ArtistData.descriptionLenght}
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
          <Button disabled={loading} onClick={onCancel}>
            Cancel
          </Button>
          <ProgressButton
            loading={loading}
            disabled={artist.name == ""}
            onClick={postArtist}
            text="Create"
          />
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
