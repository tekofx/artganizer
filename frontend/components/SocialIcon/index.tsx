import CloudIcon from "@mui/icons-material/Cloud";
import FacebookIcon from "@mui/icons-material/Facebook";
import InstagramIcon from "@mui/icons-material/Instagram";
import LanguageIcon from "@mui/icons-material/Language";
import LinkedInIcon from "@mui/icons-material/LinkedIn";
import PinterestIcon from "@mui/icons-material/Pinterest";
import RedditIcon from "@mui/icons-material/Reddit";
import TwitterIcon from "@mui/icons-material/Twitter";
import { IconButton, Tooltip } from "@mui/material";
import { Fa500Px, FaDeviantart, FaGithub, FaMastodon } from "react-icons/fa";
import Social from "../../interfaces/Social";
import { FurAffinityIcon, ItakuIcon } from "./Icons";

const socialMediaIcons = {
  instagram: <InstagramIcon />,
  facebook: <FacebookIcon />,
  twitter: <TwitterIcon />,
  linkedin: <LinkedInIcon />,
  reddit: <RedditIcon />,
  pinterest: <PinterestIcon />,
  bluesky: <CloudIcon />,
  deviantart: <FaDeviantart />,
  github: <FaGithub />,
  mastodon: <FaMastodon />,
  "500px": <Fa500Px />,
  furaffinity: <FurAffinityIcon />,
  itaku: <ItakuIcon />,
};

interface SocialIconProps {
  social: Social;
  clickable?: boolean;
}

export default function SocialIcon({ social, clickable }: SocialIconProps) {
  function navigateToSocial() {
    if (clickable === false) return;
    window.open(social.url, "_blank");
  }

  var socialMediaName = social.url.toLowerCase();
  for (const [name, icon] of Object.entries(socialMediaIcons)) {
    if (socialMediaName.includes(name)) {
      return (
        <Tooltip title={social.name}>
          <IconButton
            onClick={navigateToSocial}
            sx={{ cursor: clickable ? "pointer" : "default" }}
          >
            {icon}
          </IconButton>
        </Tooltip>
      );
    }
  }

  return (
    <Tooltip title={social.name}>
      <IconButton
        onClick={navigateToSocial}
        sx={{ cursor: clickable ? "pointer" : "default" }}
      >
        <LanguageIcon />
      </IconButton>
    </Tooltip>
  );
}
