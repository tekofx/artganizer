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
import Settings from "../../../interfaces/Settings";
import { DataContext } from "../../../pages/_app";
import GallerySettings from "./Settings/GallerySettings";
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
        <GallerySettings settings={settings} setSettings={setSettings} />
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
