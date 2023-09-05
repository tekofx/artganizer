import InstagramIcon from "@mui/icons-material/Instagram";
import TwitterIcon from "@mui/icons-material/Twitter";
import LinkedInIcon from "@mui/icons-material/LinkedIn";
import CloudIcon from "@mui/icons-material/Cloud";
import RedditIcon from "@mui/icons-material/Reddit";
import PinterestIcon from "@mui/icons-material/Pinterest";
import FacebookIcon from "@mui/icons-material/Facebook";
import LanguageIcon from "@mui/icons-material/Language";
import Social from "../interfaces/Social";
import { Tooltip, IconButton } from "@mui/material";

const socialMediaIcons = {
  instagram: <InstagramIcon />,
  facebook: <FacebookIcon />,
  twitter: <TwitterIcon />,
  linkedin: <LinkedInIcon />,
  reddit: <RedditIcon />,
  pinterest: <PinterestIcon />,
  bluesky: <CloudIcon />,
};

interface SocialIconProps {
  social: Social;
}

export default function SocialIcon({ social }: SocialIconProps) {
  var socialMediaName = social.name.toLowerCase();
  for (const [name, icon] of Object.entries(socialMediaIcons)) {
    if (socialMediaName.includes(name)) {
      return (
        <Tooltip title={social.name}>
          <IconButton
            onClick={() => {
              window.open(social.url, "_blank");
            }}
            sx={{ cursor: "pointer" }}
          >
            {icon}
          </IconButton>
        </Tooltip>
      );
    }
  }

  function navigateToSocial() {
    window.open(social.url, "_blank");
  }

  return (
    <Tooltip title={social.name}>
      <IconButton onClick={navigateToSocial} sx={{ cursor: "pointer" }}>
        <LanguageIcon />
      </IconButton>
    </Tooltip>
  );
}
