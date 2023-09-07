import { useState, useContext, Dispatch, SetStateAction } from "react";
import {
  Button,
  Stack,
  Grid,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  IconButton,
} from "@mui/material";
import axios from "axios";
import { DataContext } from "../../../pages/_app";
import Tag from "../../../interfaces/Tag";
import Submission from "../../../interfaces/Submission";

import Character from "../../../interfaces/Character";
import Artist from "../../../interfaces/Artist";
import AlertMessage from "../../../interfaces/AlertMessage";
import ProgressButton from "../ProgressButon";
import BasicInfo from "./BasicInfo";
import AdvancedInfo from "./AdvancedInfo";
import { emptySubmission } from "../../../src/emptyEntities";

interface Props {
  open: boolean;
  setOpen: Dispatch<SetStateAction<boolean>>;
  setOpenSnack: Dispatch<SetStateAction<boolean>>;
  setAlertMessage: Dispatch<SetStateAction<AlertMessage>>;
}
export default function SubmissionForm({
  open,
  setOpen,
  setOpenSnack,
  setAlertMessage,
}: Props) {
  const { data, setData } = useContext(DataContext);
  const [submission, setSubmission] = useState<Submission>(emptySubmission);
  const [image, setImage] = useState<string>("/placeholder.jpg");
  const [loading, setLoading] = useState<boolean>(false);

  const [selectedTags, setSelectedTags] = useState<Tag[]>([]);

  const [selectedArtist, setSelectedArtist] = useState<Artist | undefined>(
    undefined
  );

  const [selectedCharacters, setSelectedCharacters] = useState<Character[]>([]);

  function onCancel() {
    if (setOpen != undefined) {
      setOpen(false);
    }
  }

  function onImageUpload(event: any) {
    const newSubmission = { ...submission };
    newSubmission.image = event.target.files[0];
    setSubmission(newSubmission);
    setImage(URL.createObjectURL(event.target.files[0]));
  }

  async function saveSubmission() {
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
    try {
      const response = await axios.post(
        process.env.API_URL + `/submissions`,
        formData
      );
      var newData = { ...data };
      newData.submissions.push(response.data);
      setData(newData);
      setAlertMessage({
        message: "Submission created",
        severity: "success",
      });
    } catch (error) {
      setAlertMessage({
        message: "Error creating submission",
        severity: "error",
      });
    } finally {
      // Reset form
      setLoading(false);
      setSubmission(emptySubmission);
      setImage("/placeholder.jpg");

      setOpen(false);
      setOpenSnack(true);
    }
  }

  return (
    <Dialog open={open} onClose={() => setOpen(false)} fullScreen>
      <DialogTitle>Create Submission</DialogTitle>
      <DialogContent>
        <Grid container spacing={2}>
          <Grid item xs={6}>
            <input
              accept="image/*"
              id="contained-button-file"
              multiple
              type="file"
              hidden
              onChange={onImageUpload}
            />
            <label htmlFor="contained-button-file">
              <IconButton component="span">
                <img src={image} width="100%" />
              </IconButton>
            </label>
          </Grid>
          <Grid item xs={6}>
            <BasicInfo submission={submission} setSubmission={setSubmission} />

            {/* Advanced info */}
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
            onClick={saveSubmission}
            text="Ok"
          />

          <Button disabled={loading} onClick={() => onCancel()}>
            Cancel
          </Button>
        </Stack>
      </DialogActions>
    </Dialog>
  );
}
