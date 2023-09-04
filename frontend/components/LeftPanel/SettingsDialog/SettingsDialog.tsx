import {
  Box,
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Grid,
  IconButton,
  Stack,
  Tab,
  Tabs,
  Typography,
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
  const [value, setValue] = React.useState(0);

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
  interface TabPanelProps {
    children?: React.ReactNode;
    index: number;
    value: number;
  }

  function TabPanel(props: TabPanelProps) {
    const { children, value, index, ...other } = props;

    return (
      <div
        role="tabpanel"
        hidden={value !== index}
        id={`vertical-tabpanel-${index}`}
        aria-labelledby={`vertical-tab-${index}`}
        {...other}
      >
        {value === index && (
          <Box sx={{ p: 3 }}>
            <Typography>{children}</Typography>
          </Box>
        )}
      </div>
    );
  }
  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    setValue(newValue);
  };
  return (
    <Dialog open={props.open} fullWidth>
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
        <Grid container>
          <Grid item xs={2}>
            <Tabs
              orientation="vertical"
              variant="scrollable"
              value={value}
              sx={{ borderRight: 1, borderColor: "divider" }}
              onChange={handleChange}
            >
              <Tab label="Gallery" />
              <Tab label="Submission" />
              <Tab label="Artist" />
            </Tabs>
          </Grid>
          <Grid item xs={10}>
            <TabPanel value={value} index={0}>
              <GallerySettings settings={settings} setSettings={setSettings} />
            </TabPanel>
            <TabPanel value={value} index={1}>
              Item Two
            </TabPanel>
            <TabPanel value={value} index={2}>
              Item Three
            </TabPanel>
          </Grid>
        </Grid>
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
