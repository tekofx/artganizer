import { useState, useContext, Dispatch, SetStateAction } from "react";
import {
  Typography,
  Button,
  TextField,
  Stack,
  Rating,
  Grid,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  IconButton,
} from "@mui/material";
import axios from "axios";
import { DataContext } from "../../pages/_app";
import Tag from "../../interfaces/Tag";
import Submission from "../../interfaces/Submission";
import TagSelect from "../Tag/TagSelect";
import ArtistSelect from "../Artist/ArtistSelect";
import CharacterSelect from "../Character/CharacterSelect";
import Character from "../../interfaces/Character";
import Artist from "../../interfaces/Artist";
import AlertMessage from "../../interfaces/AlertMessage";
const emptySubmission: Submission = {
  id: 0,
  title: "",
  description: "",
  date: new Date(),
  rating: 0,
  width: 0,
  height: 0,
  folders: [],
  tags: [],
  characters: [],
  artist: undefined,
  colors: [],
  size: 0,
  format: "",
  image: "/placeholder.jpg",
};

interface Props {
  open: boolean;
  setOpen: Dispatch<SetStateAction<boolean>>;
  setOpenSnack: Dispatch<SetStateAction<boolean>>;
  setAlertMessage: Dispatch<SetStateAction<AlertMessage>>;
}
export default function SubmissionForm(props: Props) {
  const { data, setData } = useContext(DataContext);
  const [submission, setSubmission] = useState<Submission>(emptySubmission);
  const [image, setImage] = useState<string>("/placeholder.jpg");

  const [selectedTags, setSelectedTags] = useState<Tag[]>([]);

  const [selectedArtist, setSelectedArtist] = useState<Artist | undefined>(
    undefined
  );

  const [selectedCharacters, setSelectedCharacters] = useState<Character[]>([]);

  function onCancel() {
    if (props.setOpen != undefined) {
      props.setOpen(false);
    }
  }

  function onImageUpload(event: any) {
    const newSubmission = { ...submission };
    newSubmission.image = event.target.files[0];
    setSubmission(newSubmission);
    setImage(URL.createObjectURL(event.target.files[0]));
  }

  async function saveSubmission() {
    const formData = new FormData();
    formData.append("image", submission.image);
    formData.append("title", submission.title);
    formData.append("description", submission.description);
    formData.append("rating", submission.rating.toString());
    formData.append("folders", JSON.stringify(submission.folders));
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
        `http://localhost:3001/submissions`,
        formData
      );
      var newData = { ...data };
      newData.submissions.push(response.data);
      setData(newData);
      props.setAlertMessage({
        message: "Submission created",
        severity: "success",
      });
    } catch (error) {
      props.setAlertMessage({
        message: "Error creating submission",
        severity: "error",
      });
    } finally {
      // Update data

      props.setOpen(false);
      props.setOpenSnack(true);
    }
  }

  return (
    <Dialog open={props.open} onClose={() => props.setOpen(false)} fullScreen>
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
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <Typography variant="h6">Basic Info</Typography>
                <TextField
                  label="Title"
                  fullWidth
                  value={submission.title}
                  onChange={(event) => {
                    setSubmission((prevSubmission) => ({
                      ...prevSubmission,
                      title: event.target.value,
                    }));
                  }}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  label="Description"
                  fullWidth
                  value={submission.description}
                  onChange={(event) => {
                    setSubmission((prevSubmission) => ({
                      ...prevSubmission,
                      description: event.target.value,
                    }));
                  }}
                />
              </Grid>
              <Grid item xs={12}>
                <Typography variant="h6">Rating</Typography>
                <Rating
                  value={submission.rating}
                  onChange={(event, newValue) => {
                    setSubmission((prevSubmission) => ({
                      ...prevSubmission,
                      rating: newValue || 0,
                    }));
                  }}
                />
              </Grid>
              <Grid item xs={12}>
                <Typography variant="h6">Advanced Info</Typography>
              </Grid>

              <Grid item xs={6}>
                <TagSelect
                  selectedTags={selectedTags}
                  setSelectedTags={setSelectedTags}
                />
              </Grid>

              <Grid item xs={6}>
                <ArtistSelect
                  selectedArtist={selectedArtist}
                  setSelectedArtist={setSelectedArtist}
                />
              </Grid>
              <Grid item xs={12}>
                <CharacterSelect
                  selectedCharacters={selectedCharacters}
                  setSelectedCharacters={setSelectedCharacters}
                />
              </Grid>
            </Grid>
          </Grid>
        </Grid>
      </DialogContent>
      <DialogActions>
        <Stack direction="row" spacing={2}>
          <Button variant="contained" onClick={() => saveSubmission()}>
            Ok
          </Button>
          <Button onClick={() => onCancel()}>Cancel</Button>
        </Stack>
      </DialogActions>
    </Dialog>
  );
}
