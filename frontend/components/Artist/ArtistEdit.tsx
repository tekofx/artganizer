import { Grid, Avatar, Button, Stack, TextField } from "@mui/material";
import Artist from "../../interfaces/Artist";
import DoneIcon from "@mui/icons-material/Done";
import { Dispatch, SetStateAction, useContext, useState } from "react";
import axios from "axios";
import { DataContext } from "../../pages/_app";
import { useRouter } from "next/router";
interface ArtistEditProps {
  artist: Artist;
  toggleEdit: () => void;
  setArtist: Dispatch<SetStateAction<Artist | undefined>>;
}

export default function ArtistEdit(props: ArtistEditProps) {
  const [artist, setArtist] = useState<Artist>(props.artist);
  const [image, setImage] = useState<string>(props.artist.image);
  const [imageData, setImageData] = useState<any>();
  const { data, setData } = useContext(DataContext);
  const router = useRouter();

  function onImageUpload(event: any) {
    setImageData(event.target.files[0]);

    // Change the image in the preview
    setImage(URL.createObjectURL(event.target.files[0]));
  }
  async function editArtist() {
    console.log(artist.image);
    const formData = new FormData();
    formData.append("image", imageData);
    formData.append("name", artist.name);
    formData.append("description", artist.description);
    formData.append("id", artist.id.toString());

    await axios
      .put(`http://localhost:3001/artists/${artist.id}`, formData)
      .then((response) => {
        props.setArtist(response.data);
        var newData = { ...data };
        newData.artists = newData.artists.map((artist) => {
          if (artist.id === response.data.id) {
            return response.data;
          }
          return artist;
        });
        setData(newData);
      })
      .catch((error) => {
        console.log(error);
      });
    props.toggleEdit();

    // Reload the page
    router.reload();
  }

  return (
    <Grid container spacing={2}>
      <Grid item>
        <Stack spacing={2} direction="column">
          <Avatar sx={{ width: "10rem", height: "10rem" }} src={image} />
          <Button variant="contained" component="label">
            Change image
            <input type="file" hidden onChange={onImageUpload} />
          </Button>
        </Stack>
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
            onClick={() => editArtist()}
          >
            Ok
          </Button>
        </Stack>
      </Grid>
    </Grid>
  );
}
