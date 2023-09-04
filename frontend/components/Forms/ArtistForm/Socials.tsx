import {
  Stack,
  TextField,
  Typography,
  Button,
  IconButton,
} from "@mui/material";

import InstagramIcon from "@mui/icons-material/Instagram";
import TwitterIcon from "@mui/icons-material/Twitter";
import LinkedInIcon from "@mui/icons-material/LinkedIn";
import CloudIcon from "@mui/icons-material/Cloud";
import RedditIcon from "@mui/icons-material/Reddit";
import PinterestIcon from "@mui/icons-material/Pinterest";
import FacebookIcon from "@mui/icons-material/Facebook";
import LanguageIcon from "@mui/icons-material/Language";
import ClearIcon from "@mui/icons-material/Clear";

interface SocialWithIcon {
  name: string;
  url: string;
  icon?: JSX.Element;
}
interface SocialProps {
  socials: SocialWithIcon[];
  setSocials: any;
}
const socialMediaIcons = {
  instagram: <InstagramIcon />,
  facebook: <FacebookIcon />,
  twitter: <TwitterIcon />,
  linkedin: <LinkedInIcon />,
  reddit: <RedditIcon />,
  pinterest: <PinterestIcon />,
  bluesky: <CloudIcon />,
};
export default function Socials({ socials, setSocials }: SocialProps) {
  function handleSocialURLChange(event: any, index: number) {
    const newSocials = [...socials];
    var icon = getIcon(event.target.value);

    if (icon) {
      newSocials[index].icon = icon;
    } else {
      newSocials[index].icon = <LanguageIcon />;
    }
    newSocials[index].url = event.target.value;
    setSocials(newSocials);
  }

  const getIcon = (socialMediaName: string) => {
    socialMediaName = socialMediaName.toLowerCase();
    for (const [name, icon] of Object.entries(socialMediaIcons)) {
      if (socialMediaName.includes(name)) {
        return icon;
      }
    }
    return null;
  };

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
      icon: <LanguageIcon />,
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
          {value.icon}
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
