import {
  Grid,
  Avatar,
  Button,
  Stack,
  TextField,
  Typography,
  IconButton,
} from "@mui/material";
import Artist from "../../../interfaces/Artist";
import DoneIcon from "@mui/icons-material/Done";
import ClearIcon from "@mui/icons-material/Clear";
import EditIcon from "@mui/icons-material/Edit";

import { Dispatch, SetStateAction, useState } from "react";
import axios from "axios";
import SocialIcon from "../../SocialIcon";
import AddSocialDialog from "./AddSocialDialog";
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
  const [auxArtist, setAuxArtist] = useState<Artist>(artist);

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
    formData.append("name", auxArtist.name);
    formData.append("description", auxArtist.description);
    formData.append("id", auxArtist?.id.toString());
    formData.append("socials", JSON.stringify(artist.socials));

    await axios
      .put(process.env.API_URL + `/artists/${auxArtist.id}`, formData)
      .then((response) => {
        setArtist(response.data);
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
        <input
          accept="image/*"
          id="artist-edit"
          multiple
          type="file"
          hidden
          onChange={onImageUpload}
        />
        <label htmlFor="artist-edit">
          <IconButton component="span">
            <Avatar src={image} sx={{ width: "10rem", height: "10rem" }} />
          </IconButton>
        </label>
      </Grid>
      <Grid item>
        <Stack spacing={2}>
          <TextField
            label="Name"
            value={auxArtist?.name}
            onChange={(event) => {
              if (auxArtist) {
                setAuxArtist((prevSubmission) => ({
                  ...prevSubmission,
                  name: event.target.value,
                }));
              }
            }}
          />

          <TextField
            label="Description"
            multiline
            value={auxArtist?.description}
            onChange={(event) => {
              if (auxArtist) {
                setAuxArtist((prevSubmission) => ({
                  ...prevSubmission,
                  description: event.target.value,
                }));
              }
            }}
          />
        </Stack>
      </Grid>
      <Grid item >
        <Typography>Socials</Typography>
        <Stack spacing={2} direction="row">
          {artist?.socials?.map((social, index) => (
            <SocialIcon social={social} key={index} clickable />
          ))}
        </Stack>
        <Button
          variant="contained"
          startIcon={<EditIcon />}
          onClick={() => setSocialsDialogOpen(true)}
        >
          Edit socials
        </Button>
      </Grid>
      <Grid item xs></Grid>
      <Grid item>
        <Stack spacing={2}>
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

      <AddSocialDialog
        artist={artist}
        setArtist={setArtist}
        setOpen={setSocialsDialogOpen}
        open={socialsDialogOpen}
      />
    </Grid>
  );
}
