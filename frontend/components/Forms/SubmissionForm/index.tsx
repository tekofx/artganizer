import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Grid,
  IconButton,
  Stack,
} from "@mui/material";
import { Dispatch, SetStateAction, useState } from "react";
import Submission from "../../../interfaces/Submission";
import Tag from "../../../interfaces/Tag";

import AlertMessage from "../../../interfaces/AlertMessage";
import Artist from "../../../interfaces/Artist";
import Character from "../../../interfaces/Character";
import { useAppContext } from "../../../pages/_app";
import { emptySubmission } from "../../../src/emptyEntities";
import Snack from "../../Snack";
import ProgressButton from "../ProgressButon";
import AdvancedInfo from "./AdvancedInfo";
import BasicInfo from "./BasicInfo";

interface Props {
  open: boolean;
  setOpen: Dispatch<SetStateAction<boolean>>;
}
export default function SubmissionForm({ open, setOpen }: Props) {
  const [submission, setSubmission] = useState<Submission>(emptySubmission);
  const [image, setImage] = useState<string>("/placeholder.jpg");
  const [loading, setLoading] = useState<boolean>(false);
  const [disabled, setDisabled] = useState<boolean>(true);
  const [openSnack, setOpenSnack] = useState<boolean>(false);
  const [alertMessage, setAlertMessage] = useState<AlertMessage>({
    message: "Submission created",
    severity: "success",
  });

  const [selectedTags, setSelectedTags] = useState<Tag[]>([]);

  const [selectedArtist, setSelectedArtist] = useState<Artist | undefined>(
    undefined
  );

  const [selectedCharacters, setSelectedCharacters] = useState<Character[]>([]);

  const { createSubmission } = useAppContext();

  function resetForm() {
    setSubmission(emptySubmission);
    setImage("/placeholder.jpg");
    setSelectedArtist(undefined);
    setSelectedCharacters([]);
    setSelectedTags([]);
  }

  function onCancel() {
    setOpen(false);
    resetForm();
  }

  function onImageUpload(event: any) {
    const newSubmission = { ...submission };
    newSubmission.image = event.target.files[0];
    setSubmission(newSubmission);
    setImage(URL.createObjectURL(event.target.files[0]));
    setDisabled(false);
  }

  async function onOkClick() {
    setLoading(true);
    var status = await createSubmission(
      submission,
      selectedTags,
      selectedArtist,
      selectedCharacters
    );
    if (status != undefined) {
      setAlertMessage({
        message: "Submission created",
        severity: "success",
      });
    } else {
      setAlertMessage({
        message: "Error creating submission",
        severity: "error",
      });
    }
    setLoading(false);
    setOpen(false);
    setOpenSnack(true);
    resetForm();
  }

  /* async function saveSubmission() {
    setLoading(true);
    const formData = new FormData();
    formData.append("image", submission.image);
    formData.append("title", submission.title);
    formData.append("description", submission.description);
    formData.append("rating", submission.rating.toString());
    if (selectedTags != undefined) {
      selectedTags.forEach((tag) => {
        formData.append("tags", tag.id.toString());
      });
    }
    if (selectedArtist != undefined) {
      formData.append("artist", selectedArtist.id.toString());
    }
    if (selectedCharacters != undefined) {
      selectedCharacters.forEach((character) => {
        formData.append("characters", character.id.toString());
      });
    }

    // Create submission
    await axios
      .post(process.env.API_URL + `/submissions`, formData)
      .then(() => {
        setAlertMessage({
          message: "Submission created",
          severity: "success",
        });
      })
      .catch(() => {
        setAlertMessage({
          message: "Error creating submission",
          severity: "error",
        });
      })
      .finally(() => {
        // Reset form
        setLoading(false);
        setOpen(false);
        setOpenSnack(true);
        resetForm();
      });
  } */

  return (
    <>
      <Dialog open={open} onClose={() => setOpen(false)}>
        <DialogTitle>Create Submission</DialogTitle>
        <DialogContent>
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <input
                accept="image/png, image/jpeg"
                id="submission-form-image"
                multiple
                type="file"
                hidden
                onChange={onImageUpload}
              />
              <label htmlFor="submission-form-image">
                <IconButton component="span">
                  <img src={image} width="100%" />
                </IconButton>
              </label>
            </Grid>
            <Grid item xs={12}>
              <BasicInfo
                submission={submission}
                setSubmission={setSubmission}
              />

              <AdvancedInfo
                selectedTags={selectedTags}
                setSelectedTags={setSelectedTags}
                selectedArtist={selectedArtist}
                setSelectedArtist={setSelectedArtist}
                selectedCharacters={selectedCharacters}
                setSelectedCharacters={setSelectedCharacters}
              />
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <Stack direction="row" spacing={2}>
            <ProgressButton
              loading={loading}
              disabled={disabled}
              onClick={onOkClick}
              text="Ok"
            />

            <Button disabled={loading} onClick={onCancel}>
              Cancel
            </Button>
          </Stack>
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
