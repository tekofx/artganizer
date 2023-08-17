import { Grid, Avatar, Button, Stack, TextField } from "@mui/material";
import Artist from "../../interfaces/Artist";
import DoneIcon from "@mui/icons-material/Done";
import { Dispatch, SetStateAction, useState } from "react";
import axios from "axios";

interface ArtistEditProps {
  artist: Artist;
  toggleEdit: () => void;
  setArtist: Dispatch<SetStateAction<Artist | undefined>>;
}

export default function ArtistEdit(props: ArtistEditProps) {
  const [artist, setArtist] = useState<Artist>(props.artist);

  async function editArtist() {
    await axios
      .put(`http://localhost:3001/artists/${artist.id}`, {
        artist,
      })
      .then((response) => {
        props.setArtist(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
    props.toggleEdit();
  }

  return (
    <Grid container spacing={2}>
      <Grid item>
        <Avatar
          sx={{ width: "10rem", height: "10rem" }}
          src={props.artist?.image}
        />
      </Grid>
      <Grid item>
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
        </Stack>
      </Grid>
      <Grid item>
        <Stack direction="row" width="100%" spacing={2} justifyContent="center">
          <Button
            variant="contained"
            startIcon={<DoneIcon />}
            onClick={editArtist}
          >
            Ok
          </Button>
        </Stack>
      </Grid>
    </Grid>
  );
}
