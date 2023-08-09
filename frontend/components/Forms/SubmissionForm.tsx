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
            <Chip
              key={option.id}
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
  artists: [],
  folders: [],
  tags: [],
  characters: [],
  format: "",
  image: "/placeholder.jpg",
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
        await axios.post(`http://localhost:3001/submissions`, submission);
      } catch (error) {
        console.error(error);
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
          <img src={submission.image} width="100%" />
          <Button variant="contained" component="label">
            Upload File
            <input type="file" hidden />
          </Button>
        </Grid>
      </Grid>
      <Stack direction="row">
        <Button onClick={() => saveSubmission()}>Ok</Button>
        <Button onClick={() => onCancel()}>Cancel</Button>
      </Stack>
    </Paper>
  );
}
