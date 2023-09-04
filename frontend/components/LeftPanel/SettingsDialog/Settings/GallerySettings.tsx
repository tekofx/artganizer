import {
  FormControlLabel,
  FormGroup,
  Typography,
  Checkbox,
} from "@mui/material";
import Settings from "../../../../interfaces/Settings";

interface GallerySettingsProps {
  settings: Settings;
  setSettings: React.Dispatch<React.SetStateAction<Settings>>;
}

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
    <>
      <Typography variant="body2">
        What info will be displayed in the submissions gallery for each
        submission
      </Typography>
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
          control={<Checkbox onChange={(e) => handleChange(e, "dimensions")} />}
          checked={settings.galleryInfo.dimensions}
        />

        <FormControlLabel
          label="Tags"
          control={<Checkbox onChange={(e) => handleChange(e, "tags")} />}
          checked={settings.galleryInfo.tags}
        />
        <FormControlLabel
          label="Characters"
          control={<Checkbox onChange={(e) => handleChange(e, "characters")} />}
          checked={settings.galleryInfo.characters}
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
    </>
  );
}
