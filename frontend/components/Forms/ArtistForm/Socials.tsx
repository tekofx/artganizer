import {
  Button,
  IconButton,
  Stack,
  TextField,
  Typography,
} from "@mui/material";

import ClearIcon from "@mui/icons-material/Clear";
import { useEffect } from "react";
import { Artist } from "../../../interfaces";
import SocialIcon from "../../SocialIcon";

interface SocialProps {
  artist: Artist;
  setArtist: any;
}

export default function Socials({ artist, setArtist }: SocialProps) {

  useEffect(() => {
    if (artist.socials.length == 0) {
      addEmptySocial();
    }
  }
    , []);

  function handleSocialURLChange(event: any, index: number) {
    const newSocials = [...artist.socials];

    newSocials[index].url = event.target.value;
    setArtist({ ...artist, socials: newSocials });
  }

  function handleSocialNameChange(event: any, index: number) {
    const newSocials = [...artist.socials];

    newSocials[index].name = event.target.value;
    setArtist({ ...artist, socials: newSocials });
  }
  function addEmptySocial() {
    const newSocials = [...artist.socials];
    newSocials.push({
      name: "",
      url: "",
    });
    setArtist({ ...artist, socials: newSocials });
  }

  function removeSocial(index: number) {
    const newSocials = [...artist.socials];
    newSocials.splice(index, 1);
    setArtist({ ...artist, socials: newSocials });
  }
  return (
    <Stack spacing={2}>
      <Typography>Socials</Typography>

      {artist.socials.map((value, i) => (
        <Stack direction="row" alignItems="center" spacing={2} key={i}>
          <SocialIcon social={value} clickable={false} />
          <TextField
            label="Social Name"
            value={value.name}
            onChange={(event) => {
              handleSocialNameChange(event, i);
            }}
          />
          <TextField
            label="URL"
            value={value.url}
            onChange={(event) => {
              handleSocialURLChange(event, i);
            }}
          />

          <IconButton
            aria-label="delete"
            onClick={() => {
              removeSocial(i);
            }}
          >
            <ClearIcon />
          </IconButton>
        </Stack>
      ))}
      <Button
        variant="contained"
        onClick={() => {
          addEmptySocial();
        }}
      >
        Add Social
      </Button>
    </Stack>
  );
}
