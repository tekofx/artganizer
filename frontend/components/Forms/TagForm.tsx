import {
  FormControl,
  Stack,
  TextField,
  Paper,
  Grid,
  Typography,
} from "@mui/material";
import TagChip from "../Tag/TagChip";
import TagLabel from "../Tag/TagLabel";
import Tag from "../../interfaces/Tag";
import { useState } from "react";
import { TwitterPicker, ColorResult } from "react-color";

const defaultTag: Tag = {
  name: "Tag",
  color: "#FFFFFF",
  id: -1,
  submissionCount: 0,
};

export default function TagFormm() {
  const [tag, setTag] = useState<Tag>(defaultTag);

  const handleChangeComplete = (color: ColorResult) => {
    setTag((prevTag) => ({
      ...prevTag,
      color: color.hex,
    }));
  };

  return (
    <Paper sx={{ p: 2 }}>
      <Grid container spacing={2}>
        <Grid item lg={12}>
          <Typography>Create Tag</Typography>
        </Grid>
        <Grid item lg={6}>
          <FormControl fullWidth>
            <Stack spacing={2}>
              <TextField
                label="Name"
                value={tag.name}
                onChange={(event) => {
                  setTag((prevSubmission) => ({
                    ...prevSubmission,
                    name: event.target.value,
                  }));
                }}
              />
              <TwitterPicker
                triangle="hide"
                color={tag.color}
                onChangeComplete={handleChangeComplete}
              />
            </Stack>
          </FormControl>
        </Grid>
        <Grid item lg={6}>
          <TagLabel tag={tag} />
          <TagChip tag={tag} />
        </Grid>
      </Grid>
    </Paper>
  );
}
