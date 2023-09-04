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
import LanguageIcon from "@mui/icons-material/Language";
import LimitedTextField from "../../LimitedTextField";
import Socials from "./Socials";
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

const defaultSocials: SocialWithIcon[] = [
  {
    name: "",
    url: "",
    icon: <LanguageIcon />,
  },
];

interface SocialWithIcon {
  name: string;
  url: string;
  icon?: JSX.Element;
}

interface Props {
  open: boolean;
  setOpen: React.Dispatch<React.SetStateAction<boolean>>;
  setOpenSnack: React.Dispatch<React.SetStateAction<boolean>>;
  setAlertMessage: React.Dispatch<React.SetStateAction<AlertMessage>>;
}

export default function ArtistForm({
  open,
  setOpen,
  setOpenSnack,
  setAlertMessage,
}: Props) {
  const [artist, setArtist] = useState<Artist>(defaultArtist);
  const [image, setImage] = useState<string>("/placeholder.jpg");
  const [socials, setSocials] = useState<SocialWithIcon[]>(defaultSocials);

  const { data, setData } = useContext(DataContext);

  function onImageUpload(event: any) {
    const newArtist = { ...artist };
    newArtist.image = event.target.files[0];
    setArtist(newArtist);
    setImage(URL.createObjectURL(event.target.files[0]));
  }

  async function postArtist(artist: Artist) {
    const formData = new FormData();
    formData.append("name", artist.name);
    formData.append("description", artist.description);
    formData.append("image", artist.image);
    // Remove icon from socials
    for (const social of socials) {
      const socialWithoutIcon = { ...social };
      delete socialWithoutIcon.icon;
      formData.append("socials", JSON.stringify(socialWithoutIcon));
    }
    artist.socials = socials;

    await axios
      .post("http://localhost:3001/artists", formData)
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
        setOpenSnack(true);
      });
  }

  return (
    <Dialog open={open} onClose={() => setOpen(false)}>
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
            <Socials socials={socials} setSocials={setSocials} />
          </Grid>
        </Grid>
      </DialogContent>
      <DialogActions>
        <Button
          variant="contained"
          onClick={() => {
            postArtist(artist);
            setOpen(false);
          }}
        >
          Create
        </Button>
        <Button onClick={() => setOpen(false)}>Cancel</Button>
      </DialogActions>
    </Dialog>
  );
}
