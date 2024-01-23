import {
  Stack,
  TextField,
  Grid,
  Typography,
  Button,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
} from "@mui/material";
import TagChip from "../../Tag/TagChip";
import TagLabel from "../../Tag/TagLabel";
import Tag from "../../../interfaces/Tag";
import { useState } from "react";
import axios from "axios";
import { TwitterPicker, ColorResult } from "react-color";
import AlertMessage from "../../../interfaces/AlertMessage";
import ProgressButton from "../ProgressButon";
import Snack from "../../Snack";
const defaultTag: Tag = {
  name: "",
  color: "#FFFFFF",
  id: -1,
  submissionCount: 0,
};

interface Props {
  open: boolean;
  setOpen: React.Dispatch<React.SetStateAction<boolean>>;
  tagToUpdate?: Tag;
}

export default function TagForm({ open, setOpen, tagToUpdate }: Props) {
  const [tag, setTag] = useState<Tag>(tagToUpdate ?? defaultTag);
  const [loading, setLoading] = useState<boolean>(false);
  const [openSnack, setOpenSnack] = useState<boolean>(false);
  const [alertMessage, setAlertMessage] = useState<AlertMessage>({
    message: "Submission created",
    severity: "success",
  });


  const handleChangeComplete = (color: ColorResult) => {
    setTag((prevTag) => ({
      ...prevTag,
      color: color.hex,
    }));
  };

  function onCancel() {
    setOpen(false);
    setTag(defaultTag);
  }

  async function postTag() {
    setLoading(true);
    // Create tag
    if (tagToUpdate == undefined) {
      await axios
        .post(process.env.API_URL + `/tags`, tag)
        .then(() => {
          setAlertMessage?.({
            message: "Tag created",
            severity: "success",
          });
        })
        .catch((error) => {
          setAlertMessage?.({
            message: "Error creating tag",
            severity: "error",
          });

          console.log(error);
        })
        .finally(() => {
          setOpenSnack?.(true);
          setOpen(false);
        });
    } else {
      // Edit tag
      await axios
        .put(process.env.API_URL + `/tags/${tag.id}`, tag)
        .then(() => {
          setAlertMessage?.({
            message: "Tag updated",
            severity: "success",
          });
        })
        .catch((error) => {
          setAlertMessage?.({
            message: "Error updating tag",
            severity: "error",
          });

          console.log(error);
        })
        .finally(() => {
          setOpenSnack?.(true);
          setOpen(false);
        });
    }
    setLoading(false);
    setTag(defaultTag);
  }

  return (
    <>
      <Dialog open={open} onClose={() => setOpen(false)}>
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
            <Grid item>
              <Typography>Preview</Typography>
              <Stack direction="column" spacing={2}>
                <TagLabel tag={tag} />
                <TagChip tag={tag} />
              </Stack>
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <ProgressButton
            loading={loading}
            disabled={tag.name == ""}
            onClick={postTag}
            text={tagToUpdate == undefined ? "Create" : "Update"}
          />

          <Button disabled={loading} onClick={onCancel}>
            Close
          </Button>
        </DialogActions>
      </Dialog>
      <Snack
        open={openSnack}
        setOpen={setOpenSnack}
        alertMessage={alertMessage}
      />
    </>
  );
}
