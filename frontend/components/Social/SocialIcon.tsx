import InstagramIcon from "@mui/icons-material/Instagram";
import TwitterIcon from "@mui/icons-material/Twitter";
import LinkedInIcon from "@mui/icons-material/LinkedIn";
import CloudIcon from "@mui/icons-material/Cloud";
import RedditIcon from "@mui/icons-material/Reddit";
import PinterestIcon from "@mui/icons-material/Pinterest";
import FacebookIcon from "@mui/icons-material/Facebook";
import LanguageIcon from "@mui/icons-material/Language";
import Social from "../../interfaces/Social";
import { FaDeviantart, FaGithub, FaMastodon, Fa500Px } from "react-icons/fa";
import { FurAffinityIcon, ItakuIcon } from "./Icons";
import { Tooltip, IconButton } from "@mui/material";

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
