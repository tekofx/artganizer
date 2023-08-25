import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  FormControlLabel,
  FormGroup,
  IconButton,
  Stack,
  Typography,
  Checkbox,
} from "@mui/material";
import React, { Dispatch, SetStateAction } from "react";
import ClearIcon from "@mui/icons-material/Clear";
import axios from "axios";
import { useState, useContext } from "react";
import Settings from "../interfaces/Settings";
import { DataContext } from "../pages/_app";
interface SettingsDialogProps {
  open: boolean;
  setOpen: Dispatch<SetStateAction<boolean>>;
}
export default function SettingsDialog(props: SettingsDialogProps) {
  const { data, setData } = useContext(DataContext);
  const [settings, setSettings] = useState<Settings>(data.settings);

  async function updateSettings() {
    await axios
      .put("http://localhost:3001/settings", settings)
      .then(() => {
        setData({
          ...data,
          settings: settings,
        });
      })
      .catch((err) => {
        console.log(err);
      })
      .finally(() => {
        props.setOpen(false);
      });
  }

  async function resetSettings() {
    await axios
      .delete("http://localhost:3001/settings")
      .then((res) => {
        setData({
          ...data,
          settings: res.data,
        });
      })
      .catch((err) => {
        console.log(err);
      })
      .finally(() => {
        props.setOpen(false);
      });
  }

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
    <Dialog open={props.open}>
      <DialogTitle>
        <Stack
          direction="row"
          alignItems="center"
          spacing={2}
          justifyContent="space-between"
        >
          <Typography variant="h6">Settings</Typography>
          <IconButton onClick={() => props.setOpen(false)}>
            <ClearIcon />
          </IconButton>
        </Stack>
      </DialogTitle>
      <DialogContent>
        <Typography variant="h6">General</Typography>
        <FormGroup>
          <FormControlLabel label="Show lateral menu" control={<Checkbox />} />
          <FormControlLabel
            label="Show number of submissions in tags"
            control={<Checkbox />}
          />
        </FormGroup>
        <Typography variant="h6">Content</Typography>
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
            label="Characters"
            control={
              <Checkbox onChange={(e) => handleChange(e, "characters")} />
            }
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
      </DialogContent>
      <DialogActions>
        <Button onClick={updateSettings} variant="contained" color="primary">
          Save
        </Button>
        <Button onClick={resetSettings} variant="outlined" color="primary">
          Reset to default
        </Button>
        <Button variant="outlined" onClick={() => props.setOpen(false)}>
          Close
        </Button>
      </DialogActions>
    </Dialog>
  );
}
