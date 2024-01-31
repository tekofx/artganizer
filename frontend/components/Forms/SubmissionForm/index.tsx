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
    submission.characters = selectedCharacters;
    submission.tags = selectedTags;
    submission.artist = selectedArtist;
    try {
      await createSubmission(submission);
      setAlertMessage({
        message: "Submission created",
        severity: "success",
      });
    } catch (e: any) {
      console.log(e.message);
      setAlertMessage({
        message: e.message,
        severity: "error",
      });
    }


    setLoading(false);
    setOpen(false);
    setOpenSnack(true);
    resetForm();
  }
  return (
    <>
      <Dialog open={open} onClose={() => setOpen(false)} maxWidth={"xl"}>
        <DialogTitle>Create Submission</DialogTitle>
        <DialogContent>
          <Grid container spacing={2}>
            <Grid item xs={12} lg={6}>
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
            <Grid item xs={12} lg={6}>
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
            <Button disabled={loading} onClick={onCancel}>
              Cancel
            </Button>
            <ProgressButton
              loading={loading}
              disabled={disabled}
              onClick={onOkClick}
              text="Ok"
            />
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
