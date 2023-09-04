import InstagramIcon from "@mui/icons-material/Instagram";
import TwitterIcon from "@mui/icons-material/Twitter";
import LinkedInIcon from "@mui/icons-material/LinkedIn";
import CloudIcon from "@mui/icons-material/Cloud";
import RedditIcon from "@mui/icons-material/Reddit";
import PinterestIcon from "@mui/icons-material/Pinterest";
import FacebookIcon from "@mui/icons-material/Facebook";
import LanguageIcon from "@mui/icons-material/Language";

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
  socialMediaName: string;
}

export default function SocialIcon({ socialMediaName }: SocialIconProps) {
  socialMediaName = socialMediaName.toLowerCase();
  for (const [name, icon] of Object.entries(socialMediaIcons)) {
    if (socialMediaName.includes(name)) {
      return icon;
    }
  }
  return <LanguageIcon />;
}
