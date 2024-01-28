import {
  Checkbox,
  FormControlLabel,
  FormGroup,
  Grid,
  Typography,
} from "@mui/material";
import { Settings, Submission } from "../../../../../interfaces";
import Image from "../../../../Gallery/Image";

interface GallerySettingsProps {
  settings: Settings;
  setSettings: React.Dispatch<React.SetStateAction<Settings>>;
}

var testSubmission: Submission = {
  id: 0,
  title: "Title",
  description: " description",
  date: new Date(),
  format: "PNG",
  image: "/placeholder.jpg",
  original_image: "/placeholder.jpg",
  thumbnail: "/placeholder.jpg",
  filename: "image.png",
  colors: ["#FFFFFF"],
  height: 200,
  width: 400,
  rating: 3,
  size: 200,
  artist: {
    id: 1,
    description: "Description",
    image: "/placeholder.jpg",
    name: "Artist",
    socials: [],
    submissions: [],
  },
  tags: [
    {
      id: 0,
      color: "#FFFFFF",
      name: "Tag",
      submissionCount: 1,
    },
  ],
  characters: [
    {
      description: "Description",
      id: 1,
      name: "Character",
      image: "/placeholder.jpg",
    },
  ],
};

export default function GallerySettings({
  settings,
  setSettings,
}: GallerySettingsProps) {
  function handleChange(e: React.ChangeEvent<HTMLInputElement>, field: string) {
    setSettings({
      ...settings,
      galleryInfo: {
        ...settings.galleryInfo,
        [field]: e.target.checked,
      },
    });
  }
  return (
    <Grid container spacing={2}>
      <Grid item xs={12}>
        <Typography variant="body2">
          What info will be displayed in the submissions gallery for each
          submission
        </Typography>
      </Grid>
      <Grid item xs={6}>
        <FormGroup>
          <FormControlLabel
            label="Title"
            control={<Checkbox onChange={(e) => handleChange(e, "title")} />}
            checked={settings.galleryInfo.title}
          />
          <FormControlLabel
            label="Date"
            control={<Checkbox onChange={(e) => handleChange(e, "date")} />}
            checked={settings.galleryInfo.date}
          />
          <FormControlLabel
            label="Rating"
            control={<Checkbox onChange={(e) => handleChange(e, "rating")} />}
            checked={settings.galleryInfo.rating}
          />
          <FormControlLabel
            label="Dimensions"
            control={
              <Checkbox onChange={(e) => handleChange(e, "dimensions")} />
            }
            checked={settings.galleryInfo.dimensions}
          />

          <FormControlLabel
            label="Tags"
            control={<Checkbox onChange={(e) => handleChange(e, "tags")} />}
            checked={settings.galleryInfo.tags}
          />

          <FormControlLabel
            label="Artist"
            control={<Checkbox onChange={(e) => handleChange(e, "artist")} />}
            checked={settings.galleryInfo.artist}
          />

          <FormControlLabel
            label="Colors"
            control={<Checkbox onChange={(e) => handleChange(e, "colors")} />}
            checked={settings.galleryInfo.colors}
          />
        </FormGroup>
      </Grid>
      <Grid item xs={6}>
        <Image submission={testSubmission} />
      </Grid>
    </Grid>
  );
}
