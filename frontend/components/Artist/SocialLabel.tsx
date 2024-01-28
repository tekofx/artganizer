import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import { IconButton, Paper, Stack, Typography } from "@mui/material";
import axios from "axios";
import { Dispatch, SetStateAction, useState } from "react";
import { Artist, Social } from "../../interfaces";
import SocialIcon from "../SocialIcon";
import SocialDialog from "./ArtistEdit/SocialDialog";
interface SocialLabelProps {
  social: Social;
  artist: Artist;
  setArtist: Dispatch<SetStateAction<Artist>>;
}

export default function SocialLabel({
  social,
  artist,
  setArtist,
}: SocialLabelProps) {
  const [socialDialogOpen, setSocialDialogOpen] = useState(false);

  async function removeSocial() {
    await axios
      .delete(
        process.env.API_URL + `/artists/${artist.id}/socials/${social.id}`
      )
      .then(() => {
        setArtist({
          ...artist,
          socials: artist.socials.filter((s) => s.id != social.id),
        });
      })
      .catch((error) => {
        console.log(error);
      })
      .finally(() => {});
  }

  async function editSocial() {
    setSocialDialogOpen(true);
  }

  return (
    <Paper elevation={10}>
      <Stack direction="row" spacing={1} alignItems="center">
        <Stack direction="row" spacing={0} alignItems="center">
          <SocialIcon social={social} />

          <Typography>{social.name}</Typography>
        </Stack>
        <IconButton onClick={editSocial}>
          <EditIcon />
        </IconButton>
        <IconButton onClick={removeSocial}>
          <DeleteIcon />
        </IconButton>
      </Stack>
      <SocialDialog
        artist={artist}
        setArtist={setArtist}
        setOpen={setSocialDialogOpen}
        open={socialDialogOpen}
        social={social}
      />
    </Paper>
  );
}
