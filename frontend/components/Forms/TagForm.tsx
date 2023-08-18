import {
  Stack,
  TextField,
  Grid,
  Typography,
  Button,
  Snackbar,
  Alert,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
} from "@mui/material";
import TagChip from "../Tag/TagChip";
import TagLabel from "../Tag/TagLabel";
import Tag from "../../interfaces/Tag";
import { useState, useContext } from "react";
import axios from "axios";
import { TwitterPicker, ColorResult } from "react-color";
import { DataContext } from "../../pages/_app";

interface AlertMessage {
  message: string;
  severity: "success" | "error" | "info" | "warning";
}
const defaultTag: Tag = {
  name: "",
  color: "#FFFFFF",
  id: -1,
  submissionCount: 0,
};

const defaultAlertMessage: AlertMessage = {
  message: "Tag created",
  severity: "success",
};

interface Props {
  open: boolean;
  setOpen: React.Dispatch<React.SetStateAction<boolean>>;
}

export default function TagForm(props: Props) {
  const [tag, setTag] = useState<Tag>(defaultTag);
  const [open, setOpen] = useState<boolean>(false);
  const [alertMessage, setAlertMessage] =
    useState<AlertMessage>(defaultAlertMessage);

  const { data, setData } = useContext(DataContext);

  const handleChangeComplete = (color: ColorResult) => {
    setTag((prevTag) => ({
      ...prevTag,
      color: color.hex,
    }));
  };

  const handleClose = (
    event?: React.SyntheticEvent | Event,
    reason?: string
  ) => {
    if (reason === "clickaway") {
      return;
    }

    setOpen(false);
  };

  async function postTag(tag: Tag) {
    const response = await axios.post(`http://localhost:3001/tags`, tag);
    if (response.status != 200) {
      setAlertMessage({ message: "Error creating tag", severity: "error" });
    }

    const newData = { ...data };
    newData.tags.push(response.data);
    setData(newData);
    setOpen(true);
    props.setOpen(false);
  }

  return (
    <>
      <Dialog open={props.open} onClose={() => props.setOpen(false)}>
        <DialogTitle>Create Tag</DialogTitle>
        <DialogContent>
          <Grid container spacing={2} paddingTop={2}>
            <Grid item lg={6}>
              <TextField
                label="Name"
                value={tag.name}
                fullWidth
                onChange={(event) => {
                  setTag((prevSubmission) => ({
                    ...prevSubmission,
                    name: event.target.value,
                  }));
                }}
              />
            </Grid>
            <Grid item lg={6}>
              <TwitterPicker
                triangle="hide"
                color={tag.color}
                onChangeComplete={handleChangeComplete}
                styles={{
                  default: {
                    card: {
                      boxShadow: "none",
                      backgroundColor: "transparent",
                    },
                    body: {
                      padding: 0,
                    },
                  },
                }}
              />
            </Grid>
            <Grid item lg={6}>
              <Typography>Preview</Typography>
              <Stack direction="row" spacing={5}>
                <TagLabel tag={tag} />
                <TagChip tag={tag} />
              </Stack>
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <Button variant="contained" onClick={() => postTag(tag)}>
            Create
          </Button>

          <Button onClick={() => props.setOpen(false)}>Close</Button>
        </DialogActions>
      </Dialog>
      <Snackbar
        open={open}
        autoHideDuration={6000}
        onClose={handleClose}
        anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
      >
        <Alert
          onClose={handleClose}
          severity={alertMessage.severity}
          sx={{ width: "100%" }}
        >
          {alertMessage.message}
        </Alert>
      </Snackbar>
    </>
  );
}
