import { Dispatch, SetStateAction, useState, MouseEvent } from "react";
import {
  Paper,
  Typography,
  TextField,
  Popper,
  Stack,
  Grid,
  IconButton,
  Tooltip,
} from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import SelectableTagList from "./SelectableTagList";
import Tag from "../../interfaces/Tag";
import TagChip from "./TagChip";
import { TagForm } from "../Forms";
interface TagSelectProps {
  setSelectedTags: Dispatch<SetStateAction<Tag[]>>;
  selectedTags: Tag[];
}
export default function TagSelect({
  selectedTags,
  setSelectedTags,
}: TagSelectProps) {
  const [open, setOpen] = useState(false);
  const [openTagForm, setOpenTagForm] = useState(false);
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);

  const [filter, setFilter] = useState<string>("");

  const handleClick = (event: MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
    if (!open) {
      setOpen((previousOpen) => !previousOpen);
    }
  };

  function removeTag(tag: Tag) {
    setSelectedTags(selectedTags.filter((t) => t.id !== tag.id));
  }

  return (
    <Paper elevation={0} sx={{ p: 1 }}>
      <Grid container spacing={1}>
        <Grid item xs={12}>
          <Typography>Tags</Typography>
        </Grid>
        <Grid item xs={12}>
          <Grid container spacing={1}>
            {selectedTags.map((tag) => (
              <Grid item key={tag.id}>
                <Stack direction="row" spacing={2} alignItems="center">
                  <TagChip tag={tag} onDelete={() => removeTag(tag)} />
                </Stack>
              </Grid>
            ))}
          </Grid>
        </Grid>

        {selectedTags.length < 1 && (
          <>
            <Typography>No tags selected</Typography>
          </>
        )}
        <Grid item xs={10}>
          <TextField
            label="Search tags"
            variant="standard"
            size="small"
            fullWidth
            value={filter}
            onClick={handleClick}
            onChange={(event) => {
              setFilter(event.target.value);
            }}
          />
        </Grid>
        <Grid item xs={2}>
          <Tooltip title="Create new tag">
            <IconButton onClick={() => setOpenTagForm(true)}>
              <AddIcon />
            </IconButton>
          </Tooltip>
        </Grid>
        <Popper open={open} anchorEl={anchorEl} sx={{ zIndex: 2000 }}>
          <Paper sx={{ width: "200px" }}>
            <SelectableTagList
              selectedTags={selectedTags}
              setSelectedTags={setSelectedTags}
              setOpen={setOpen}
              filter={filter}
            />
          </Paper>
        </Popper>
        <TagForm open={openTagForm} setOpen={setOpenTagForm} />
      </Grid>
    </Paper>
  );
}
