import { Dispatch, SetStateAction, useState, MouseEvent } from "react";
import {
  Paper,
  Typography,
  TextField,
  Popper,
  Stack,
  Grid,
} from "@mui/material";
import SelectableTagList from "./SelectableTagList";
import Tag from "../../interfaces/Tag";
import TagChip from "./TagChip";
interface TagSelectProps {
  setSelectedTags: Dispatch<SetStateAction<Tag[]>>;
  selectedTags: Tag[];
}
export default function TagSelect(props: TagSelectProps) {
  const [open, setOpen] = useState(false);
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);

  const [filter, setFilter] = useState<string>("");

  const handleClick = (event: MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
    if (!open) {
      setOpen((previousOpen) => !previousOpen);
    }
  };

  function removeTag(tag: Tag) {
    props.setSelectedTags(props.selectedTags.filter((t) => t.id !== tag.id));
  }

  return (
    <Grid container>
      <Grid item xs={12}>
        <Typography>Tags select</Typography>
      </Grid>
      <Grid container spacing={1}>
        {props.selectedTags.map((tag) => (
          <Grid item key={tag.id}>
            <Stack direction="row" spacing={2} alignItems="center">
              <TagChip tag={tag} onDelete={() => removeTag(tag)} />
            </Stack>
          </Grid>
        ))}
      </Grid>

      {props.selectedTags.length < 1 && (
        <>
          <Typography>No tags selected</Typography>
        </>
      )}
      <Grid item xs={12}>
        <TextField
          label="Search tags"
          variant="standard"
          size="small"
          value={filter}
          onClick={handleClick}
          onChange={(event) => {
            setFilter(event.target.value);
          }}
        />
      </Grid>
      <Popper open={open} anchorEl={anchorEl} sx={{ zIndex: 2000 }}>
        <Paper sx={{ width: "200px" }}>
          <SelectableTagList
            selectedTags={props.selectedTags}
            setSelectedTags={props.setSelectedTags}
            setOpen={setOpen}
            filter={filter}
          />
        </Paper>
      </Popper>
    </Grid>
  );
}
