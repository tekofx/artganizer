import {
  Stack,
  TextField,
  Typography,
  Button,
  IconButton,
} from "@mui/material";
import Social from "../../../interfaces/Social";

import ClearIcon from "@mui/icons-material/Clear";
import SocialIcon from "../../SocialIcon";

interface SocialProps {
  socials: Social[];
  setSocials: any;
}

export default function Socials({ socials, setSocials }: SocialProps) {
  function handleSocialURLChange(event: any, index: number) {
    const newSocials = [...socials];

    newSocials[index].url = event.target.value;
    setSocials(newSocials);
  }

  function handleSocialNameChange(event: any, index: number) {
    const newSocials = [...socials];

    newSocials[index].name = event.target.value;
    setSocials(newSocials);
  }
  function addEmptySocial() {
    const newSocials = [...socials];
    newSocials.push({
      name: "",
      url: "",
    });
    setSocials(newSocials);
  }

  function removeSocial(index: number) {
    const newSocials = [...socials];
    newSocials.splice(index, 1);
    setSocials(newSocials);
  }
  return (
    <Stack spacing={2}>
      <Typography>Socials</Typography>

      {socials.map((value, i) => (
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
