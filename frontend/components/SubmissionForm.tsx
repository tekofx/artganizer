import { useState, useContext, Dispatch, SetStateAction } from "react";
import {
  Paper,
  Typography,
  Button,
  TextField,
  Stack,
  FormControl,
  Select,
  Rating,
  Grid,
  MenuItem,
  SelectChangeEvent,
  OutlinedInput,
  InputLabel,
  ListItemText,
  Box,
  Chip,
} from "@mui/material";
import axios from "axios";
import { DataType } from "../pages/_app";
import Checkbox from "@mui/material/Checkbox";
import { Theme, useTheme } from "@mui/material/styles";
import { DataContext } from "../pages/_app";
import Tag from "../interfaces/Tag";
import Submission from "../interfaces/Submission";

interface TagSelectProps {
  setSelectedTags: Dispatch<SetStateAction<Tag[]>>;
  selectedTags: Tag[];
}
function TagSelect(props: TagSelectProps) {
  const { data, setData } = useContext(DataContext);

  return (
    <Paper>
      <Typography>Tag select</Typography>
      {data.tags.map((tag) => (
        <Chip
          key={tag.id}
          label={tag.name}
          sx={{ backgroundColor: tag.color }}
        />
      ))}
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
  artists: [],
  folders: [],
  tags: [],
  characters: [],
  format: "",
  image: "",
};

interface SubmissionFormProps {
  submission?: Submission;
  setSubmission?: Dispatch<SetStateAction<Submission | undefined>>;
  open?: boolean;
  setOpen?: Dispatch<SetStateAction<boolean>>;
}

export default function SubmissionForm(props: SubmissionFormProps) {
  const { data, setData } = useContext(DataContext);
  const [submission, setSubmission] = useState<Submission>(
    props.submission || emptySubmission
  );

  const [selectedTags, setSelectedTags] = useState<Tag[]>(
    props.submission?.tags || []
  );

  function onCancel() {
    if (props.setOpen != undefined) {
      props.setOpen(false);
    }
  }

  async function saveSubmission() {
    console.log(submission);
    if (props.submission === undefined) {
      // Create submission
      try {
        const response = await axios.post(
          `http://localhost:3001/submissions`,
          submission
        );
      } catch (error) {
        console.error(error);
      }
    } else {
      // Update submission
      try {
        const response = await axios.put(
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
        setSubmission(submission);
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
      <Typography>Create Submission</Typography>
      <Grid container spacing={2}>
        <Grid item xs={6}>
          <FormControl fullWidth>
            <Stack>
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
          <img src={submission.image} width="100%" />
        </Grid>
      </Grid>
      <Stack direction="row">
        <Button onClick={() => saveSubmission()}>Ok</Button>
        <Button onClick={() => onCancel()}>Cancel</Button>
      </Stack>
    </Paper>
  );
}
