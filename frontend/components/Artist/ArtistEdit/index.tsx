import { Grid, Avatar, Button, Stack, TextField } from "@mui/material";
import Artist from "../../../interfaces/Artist";
import DoneIcon from "@mui/icons-material/Done";
import ClearIcon from "@mui/icons-material/Clear";

import { Dispatch, SetStateAction, useContext, useState } from "react";
import axios from "axios";
import { DataContext } from "../../../pages/_app";
import SocialIcon from "../../SocialIcon";
import SocialsDialog from "./SocialsDialog";
interface ArtistEditProps {
  artist: Artist;
  toggleEdit: () => void;
  setArtist: Dispatch<SetStateAction<Artist>>;
}

export default function ArtistEdit({
  artist,
  toggleEdit,
  setArtist,
}: ArtistEditProps) {
  const [image, setImage] = useState<string>(artist.image);
  const [imageData, setImageData] = useState<any>();
  const [socialsDialogOpen, setSocialsDialogOpen] = useState(false);
  const { data, setData } = useContext(DataContext);

  function onImageUpload(event: any) {
    setImageData(event.target.files[0]);

    // Change the image in the preview
    setImage(URL.createObjectURL(event.target.files[0]));
  }
  async function editArtist() {
    const formData = new FormData();
    if (imageData) {
      formData.append("image", imageData);
    }
    formData.append("name", artist.name);
    formData.append("description", artist.description);
    formData.append("id", artist?.id.toString());
    formData.append("socials", JSON.stringify(artist.socials));
    console.log("a");

    await axios
      .put(process.env.API_URL + `/artists/${artist.id}`, formData)
      .then((response) => {
        console.log(response.data);
        setArtist(response.data);
        setData({ ...data, artists: [...data.artists, response.data] });
      })
      .catch((error) => {
        console.log(error);
      });
    toggleEdit();

    // Reload the page
    //router.reload();
  }

  return (
    <Grid container spacing={2} sx={{ p: 4 }}>
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
            value={artist?.name}
            onChange={(event) => {
              if (artist) {
                setArtist((prevSubmission) => ({
                  ...prevSubmission,
                  name: event.target.value,
                }));
              }
            }}
          />

          <TextField
            label="Description"
            multiline
            value={artist?.description}
            onChange={(event) => {
              if (artist) {
                setArtist((prevSubmission) => ({
                  ...prevSubmission,
                  description: event.target.value,
                }));
              }
            }}
          />
        </Stack>
      </Grid>
      <Grid item>
        <Stack spacing={2}>
          {artist?.socials.map((social, index) => (
            <SocialIcon social={social} key={index} clickable />
          ))}
        </Stack>
        <Button
          variant="contained"
          startIcon={<DoneIcon />}
          onClick={() => setSocialsDialogOpen(true)}
        >
          Edit socials
        </Button>
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
          <Button
            variant="contained"
            startIcon={<ClearIcon />}
            onClick={() => toggleEdit()}
          >
            Cancel
          </Button>
        </Stack>
      </Grid>
      <SocialsDialog
        artist={artist}
        setArtist={setArtist}
        setOpen={setSocialsDialogOpen}
        open={socialsDialogOpen}
      />
    </Grid>
  );
}
