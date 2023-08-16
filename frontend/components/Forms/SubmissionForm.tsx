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
  Chip,
  Autocomplete,
  Alert,
  Snackbar,
} from "@mui/material";
import TagChip from "../Tag/TagChip";
import axios from "axios";
import { DataContext } from "../../pages/_app";
import Tag from "../../interfaces/Tag";
import Submission from "../../interfaces/Submission";
interface TagSelectProps {
  setSelectedTags: Dispatch<SetStateAction<Tag[]>>;
  selectedTags: Tag[];
}
function TagSelect(props: TagSelectProps) {
  const { data } = useContext(DataContext);

  return (
    <Paper>
      <Typography>Tag select</Typography>
      <Stack spacing={1} direction="row">
        {data.tags.map((tag) => (
          <TagChip key={tag.id} tag={tag} />
        ))}
      </Stack>

      <Autocomplete
        multiple
        id="tags-standard"
        options={data.tags}
        getOptionLabel={(option) => option.name}
        value={props.selectedTags}
        onChange={(event, value) => props.setSelectedTags(value)}
        renderInput={(params) => (
          <TextField
            {...params}
            variant="standard"
            label="Multiple values"
            placeholder="Favorites"
          />
        )}
        renderTags={(value, getTagProps) =>
          value.map((option, index) => (
            // eslint-disable-next-line react/jsx-key
            <Chip
              variant="outlined"
              label={option.name}
              style={{ backgroundColor: option.color }}
              {...getTagProps({ index })}
            />
          ))
        }
      />
    </Paper>
  );
}

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
    console.log(event.target.files[0]);
    const newSubmission = { ...submission };
    newSubmission.image = event.target.files[0];
    setSubmission(newSubmission);
    setImage(URL.createObjectURL(event.target.files[0]));
    console.log(submission);
  }

  async function saveSubmission() {
    console.log(submission);
    const formData = new FormData();
    formData.append("image", submission.image);
    formData.append("title", submission.title);
    formData.append("description", submission.description);
    formData.append("rating", submission.rating.toString());
    formData.append("folders", JSON.stringify(submission.folders));
    formData.append("tags", JSON.stringify(selectedTags));
    formData.append("artist", JSON.stringify(submission.artist));
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
        console.log(response.data);
        setData(newData);
      } catch (error) {
        console.error(error);
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
        console.log(newData);
        setData(newData);
        props.setSubmission && props.setSubmission(submission);
      } catch (error) {
        console.error(error);
      }
    }

    if (props.setOpen != undefined) {
      props.setOpen(false);
    }
  }

  return (
    <Paper sx={{ p: 2 }}>
      <Grid container spacing={2}>
        <Grid item md={12}>
          <Typography>Create Submission</Typography>
        </Grid>
        <Grid item xs={6}>
          <FormControl fullWidth>
            <Stack spacing={2}>
              <TextField
                label="Title"
                value={submission.title}
                onChange={(event) => {
                  setSubmission((prevSubmission) => ({
                    ...prevSubmission,
                    title: event.target.value,
                  }));
                }}
              />
              <TextField
                label="Description"
                value={submission.description}
                onChange={(event) => {
                  setSubmission((prevSubmission) => ({
                    ...prevSubmission,
                    description: event.target.value,
                  }));
                }}
              />
              <TagSelect
                selectedTags={selectedTags}
                setSelectedTags={setSelectedTags}
              />
              <Rating
                value={submission.rating}
                onChange={(event, newValue) => {
                  setSubmission((prevSubmission) => ({
                    ...prevSubmission,
                    rating: newValue || 0,
                  }));
                }}
              />
            </Stack>
          </FormControl>
        </Grid>
        <Grid item xs={6}>
          <img src={image} width="100%" />
          <Button variant="contained" component="label">
            Upload File
            <input type="file" hidden onChange={(e) => onImageUpload(e)} />
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
