import AddIcon from "@mui/icons-material/Add";
import ClearIcon from "@mui/icons-material/Clear";
import DoneIcon from "@mui/icons-material/Done";
import {
  Avatar,
  Button,
  Grid,
  Stack,
  Typography
} from "@mui/material";
import { Dispatch, SetStateAction, useState } from "react";
import { ArtistData } from "../../../../common/entitiesData";
import Artist from "../../../interfaces/Artist";
import { useAppContext } from "../../../pages/_app";
import LimitedTextField from "../../LimitedTextField";
import SocialLabel from "../SocialLabel";
import SocialDialog from "./SocialDialog";
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
  const { editArtist } = useAppContext();
  const [image, setImage] = useState<string>(artist.image);
  const [socialsDialogOpen, setSocialsDialogOpen] = useState(false);

  function onImageUpload(event: any) {
    const newArtist = { ...artist };
    newArtist.image = event.target.files[0];
    setArtist(newArtist);

    // Change the image in the preview
    setImage(URL.createObjectURL(event.target.files[0]));
  }
  async function onOkClick() {
    var result = await editArtist(artist);
    if (result) {
      setArtist(result);
    }
    toggleEdit();
  }

  return (
    <Grid container spacing={2} sx={{ p: 4 }}>
      <Grid item xs={12} lg display={{ xs: "flex", lg: "block" }} alignItems="center" justifyContent="center">
        <Stack spacing={2} direction="column">
          <Avatar sx={{ width: "10rem", height: "10rem" }} src={image} />
          <Button variant="contained" component="label">
            Change image
            <input type="file" hidden onChange={onImageUpload} />
          </Button>
        </Stack>
      </Grid>
      <Grid item xs={12} lg display={{ xs: "flex", lg: "block" }} alignItems="center" justifyContent="center">
        <Stack spacing={2}>
          <LimitedTextField
            label="Name"
            maxLength={ArtistData.nameLenght}
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

          <LimitedTextField
            label="Description"
            maxLength={ArtistData.descriptionLenght}
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
      <Grid item xs={12} lg display={{ xs: "flex", lg: "block" }} alignItems="center" justifyContent="center">
        <Grid container spacing={2} direction="row">
          <Grid item xs={12} lg display={{ xs: "flex", lg: "block" }} alignItems="center" justifyContent="center">

            <Typography>Socials</Typography>
          </Grid>
          {artist?.socials?.map((social, index) => (
            <Grid item key={index} xs={12} lg display={{ xs: "flex", lg: "block" }} alignItems="center" justifyContent="center">
              <SocialLabel
                social={social}
                artist={artist}
                setArtist={setArtist}
              />
            </Grid>
          ))}
          <Grid item xs={12} lg display={{ xs: "flex", lg: "block" }} alignItems="center" justifyContent="center">
            <Button
              variant="outlined"
              startIcon={<AddIcon />}
              onClick={() => setSocialsDialogOpen(true)}
            >
              Add Social
            </Button>
          </Grid>
        </Grid>
      </Grid>
      <Grid item xs></Grid>
      <Grid item xs={12} lg display={{ xs: "flex", lg: "block" }} alignItems="center" justifyContent="center">
        <Stack spacing={2} direction={{ xs: "row", lg: "column" }}>
          <Button
            variant="contained"
            fullWidth
            startIcon={<DoneIcon />}
            onClick={() => onOkClick()}
          >
            Ok
          </Button>
          <Button
            fullWidth
            variant="contained"
            startIcon={<ClearIcon />}
            onClick={() => toggleEdit()}
          >
            Cancel
          </Button>
        </Stack>
      </Grid>

      <SocialDialog
        artist={artist}
        setArtist={setArtist}
        setOpen={setSocialsDialogOpen}
        open={socialsDialogOpen}
      />
    </Grid>
  );
}
