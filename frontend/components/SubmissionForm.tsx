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
        <Chip label={tag.name} sx={{ backgroundColor: tag.color }} />
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

const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
interface SubmissionFormProps {
  submission?: Submission;
}

export default function SubmissionForm(props: SubmissionFormProps) {
  const { data, setData } = useContext(DataContext);
  const [submission, setSubmission] = useState<Submission>(
    props.submission || emptySubmission
  );

  const [selectedTags, setSelectedTags] = useState<Tag[]>(
    props.submission?.tags || []
  );

  return (
    <Paper>
      <Typography>Create Submission</Typography>
      <Grid container spacing={2}>
        <Grid item xs={6}>
          <FormControl fullWidth>
            <Stack>
              <TextField label="Title" value={submission.title} />
              <TextField label="Description" value={submission.description} />
              <TagSelect
                selectedTags={selectedTags}
                setSelectedTags={setSelectedTags}
              />
              <Rating />
              <Button>Submit</Button>
            </Stack>
          </FormControl>
        </Grid>
        <Grid item xs={6}>
          <img
            src="https://upload.wikimedia.org/wikipedia/commons/0/04/Labrador_Retriever_%281210559%29.jpg"
            width="100%"
          />
        </Grid>
      </Grid>
    </Paper>
  );
}
