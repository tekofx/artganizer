import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Grid,
  Stack,
  TextField,
  Typography,
} from "@mui/material";
import { useState } from "react";
import { ColorResult, TwitterPicker } from "react-color";
import AlertMessage from "../../../interfaces/AlertMessage";
import Tag from "../../../interfaces/Tag";
import { useAppContext } from "../../../pages/_app";
import Snack from "../../Snack";
import TagChip from "../../Tag/TagChip";
import TagLabel from "../../Tag/TagLabel";
import ProgressButton from "../ProgressButon";
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

  const { createTag, editTag } = useAppContext();

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
      var result = await createTag(tag);
      if (result) {
        setAlertMessage?.({
          message: "Tag created",
          severity: "success",
        });
      } else {
        setAlertMessage?.({
          message: "Error creating tag",
          severity: "error",
        });
      }
    } else {
      // Edit tag
      var result = await editTag(tag);
      if (result) {
        setAlertMessage?.({
          message: "Tag updated",
          severity: "success",
        });
      } else {
        setAlertMessage?.({
          message: "Error updating tag",
          severity: "error",
        });
      }
    }
    setOpenSnack?.(true);
    setOpen(false);
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
          <Button disabled={loading} onClick={onCancel}>
            Close
          </Button>
          <ProgressButton
            loading={loading}
            disabled={tag.name == ""}
            onClick={postTag}
            text={tagToUpdate == undefined ? "Create" : "Update"}
          />
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
