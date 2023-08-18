import {
  FormControl,
  Stack,
  TextField,
  Paper,
  Grid,
  Typography,
  Button,
  Snackbar,
  Alert,
  Dialog,
} from "@mui/material";
import TagChip from "../Tag/TagChip";
import TagLabel from "../Tag/TagLabel";
import Tag from "../../interfaces/Tag";
import { useState } from "react";
import axios from "axios";
import { TwitterPicker, ColorResult } from "react-color";
interface AlertMessage {
  message: string;
  severity: "success" | "error" | "info" | "warning";
}
const defaultTag: Tag = {
  name: "Tag",
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
    setOpen(true);
  }

  return (
    <Dialog open={props.open} onClose={() => props.setOpen(false)}>
      <Paper sx={{ p: 2 }}>
        <Grid container spacing={2}>
          <Grid item lg={12}>
            <Typography>Create Tag</Typography>
          </Grid>
          <Grid item lg={4}>
            <FormControl fullWidth>
              <Stack spacing={2}>
                <TextField
                  label="Name"
                  value={tag.name}
                  onChange={(event) => {
                    setTag((prevSubmission) => ({
                      ...prevSubmission,
                      name: event.target.value,
                    }));
                  }}
                />
                <TwitterPicker
                  triangle="hide"
                  color={tag.color}
                  onChangeComplete={handleChangeComplete}
                />
              </Stack>
            </FormControl>
            <Button onClick={() => postTag(tag)}>Create</Button>
          </Grid>
          <Grid item lg={4}>
            <Typography>Preview</Typography>
            <Stack direction="row" spacing={5}>
              <TagLabel tag={tag} />
              <TagChip tag={tag} />
            </Stack>
          </Grid>
          <Grid item lg={4}>
            Tag List
          </Grid>
        </Grid>

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
      </Paper>
    </Dialog>
  );
}
