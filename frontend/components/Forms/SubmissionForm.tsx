import { useState, useContext, Dispatch, SetStateAction } from "react";
import {
  Paper,
  Typography,
  Button,
  TextField,
  Stack,
  FormControl,
  Rating,
  Grid,
  Alert,
  Snackbar,
} from "@mui/material";
import axios from "axios";
import { DataContext } from "../../pages/_app";
import Tag from "../../interfaces/Tag";
import Submission from "../../interfaces/Submission";
import TagSelect from "../Tag/TagSelect";
import ArtistSelect from "../Artist/ArtistSelect";
import Artist from "../../interfaces/Artist";
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
const defaultAlertMessage: AlertMessage = {
  message: "Submission created",
  severity: "success",
};

interface SubmissionFormProps {
  submission?: Submission;
  setSubmission?: Dispatch<SetStateAction<Submission | undefined>>;
  open?: boolean;
  setOpen?: Dispatch<SetStateAction<boolean>>;
}
interface AlertMessage {
  message: string;
  severity: "success" | "error" | "info" | "warning";
}
export default function SubmissionForm(props: SubmissionFormProps) {
  const { data, setData } = useContext(DataContext);
  const [submission, setSubmission] = useState<Submission>(
    props.submission || emptySubmission
  );
  const [image, setImage] = useState<string>("/placeholder.jpg");

  const [selectedTags, setSelectedTags] = useState<Tag[]>(
    props.submission?.tags || []
  );

  const [selectedArtist, setSelectedArtist] = useState<Artist | undefined>(
    props.submission?.artist
  );

  const [open, setOpen] = useState<boolean>(false);
  const [alertMessage, setAlertMessage] =
    useState<AlertMessage>(defaultAlertMessage);

  const handleClose = (
    event?: React.SyntheticEvent | Event,
    reason?: string
  ) => {
    if (reason === "clickaway") {
      return;
    }

    setOpen(false);
  };

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
    formData.append("characters", JSON.stringify(submission.characters));

    if (props.submission === undefined) {
      // Create submission
      try {
        const response = await axios.post(
          `http://localhost:3001/submissions`,
          formData
        );
        var newData = { ...data };
        newData.submissions.push(response.data);
        setData(newData);
      } catch (error) {
        setAlertMessage({
          message: "Error creating submission",
          severity: "error",
        });
      } finally {
        // Update data

        setOpen(true);
      }
    } else {
      // Update submission
      try {
        await axios.put(
          `http://localhost:3001/submissions/${submission.id}`,
          submission
        );
        // Update data
        var newData = { ...data };
        newData.submissions = data.submissions.map((sub) =>
          sub.id === submission.id ? submission : sub
        );
        setData(newData);
        props.setSubmission && props.setSubmission(submission);
      } catch (error) {}
    }

    if (props.setOpen != undefined) {
      props.setOpen(false);
    }
  }

  return (
    <Paper sx={{ p: 2 }}>
      <Grid container spacing={2}>
        <Grid item xs={6}>
          <FormControl fullWidth>
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
            </Grid>
          </FormControl>
        </Grid>
        <Grid item xs={6}>
          <img src={image} width="100%" />
          <Button variant="contained" component="label">
            Upload File
            <input type="file" hidden onChange={onImageUpload} />
          </Button>
        </Grid>
      </Grid>
      <Stack direction="row">
        <Button onClick={() => saveSubmission()}>Ok</Button>
        <Button onClick={() => onCancel()}>Cancel</Button>
      </Stack>
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
  );
}
