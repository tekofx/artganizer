import {
  Button,
  Grid,
  IconButton,
  TextField,
  Typography
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
    <Grid container spacing={1}>
      <Grid item xs={12}>
        <Typography>Socials</Typography>
      </Grid>

      {artist.socials.map((value, i) => (
        <Grid item xs={12} key={i}>
          <Grid container spacing={2}>
            <Grid item>
              <SocialIcon social={value} clickable={false} />
            </Grid>
            <Grid item>
              <TextField
                label="Social Name"
                value={value.name}
                onChange={(event) => {
                  handleSocialNameChange(event, i);
                }}
              />
            </Grid>
            <Grid item>
              <TextField
                label="URL"
                value={value.url}
                onChange={(event) => {
                  handleSocialURLChange(event, i);
                }}
              />
            </Grid>
            <Grid item>
              <IconButton
                aria-label="delete"
                onClick={() => {
                  removeSocial(i);
                }}
              >
                <ClearIcon />
              </IconButton>
            </Grid>
          </Grid>
        </Grid>
      ))}
      <Grid item xs={12}>

        <Button
          variant="contained"
          onClick={() => {
            addEmptySocial();
          }}
        >
          Add Social
        </Button>
      </Grid>
    </Grid>
  );
}
