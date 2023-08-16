import {
  Checkbox,
  Button,
  MenuItem,
  Stack,
  Paper,
  Popover,
  Grid,
  Typography,
  Chip,
  TextField,
} from "@mui/material";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import { useState, MouseEvent, useContext } from "react";
import { DataContext } from "../../../pages/_app";
import Tag from "../../../interfaces/Tag";
import TagChip from "../../Tag/TagChip";

export default function TagFilter() {
  const { data, setData } = useContext(DataContext);
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const [searchedTags, setSearchedTags] = useState<Tag[]>(data.tags);
  const [selectedTags, setSelectedTags] = useState<Tag[]>([]);

  const open = Boolean(anchorEl);

  const onSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    var temp = data.tags.filter((tag) => tag.name.includes(event.target.value));
    console.log(temp);
    setSearchedTags(temp);
  };

  const handleClick = (event: MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  function onClick(tag: Tag) {
    if (selectedTags.includes(tag)) {
      setSelectedTags(selectedTags.filter((t) => t.id != tag.id));
      const newData = { ...data };
      newData.filters.tags = newData.filters.tags.filter((t) => t.id != tag.id);
      setData(newData);
    } else {
      setSelectedTags([...selectedTags, tag]);
      const newData = { ...data };
      newData.filters.tags = [...data.filters.tags, tag];
      setData(newData);
    }
  }

  const handleCheckboxChange = (
    event: React.ChangeEvent<HTMLInputElement>,
    tag: Tag
  ) => {
    if (event.target.checked) {
      const newData = { ...data };
      newData.filters.tags = [...data.filters.tags, tag];
      setData(newData);

      console.log("Tag filter: Added tag " + tag.name);
    } else {
      const newData = { ...data };
      newData.filters.tags = newData.filters.tags.filter((t) => t.id != tag.id);
      setData(newData);
      console.log("Tag filter: Removed tag " + tag.name);
    }
    console.log("Tag filter " + data.filters);
  };

  return (
    <div>
      <Button
        id="basic-button"
        aria-controls={open ? "basic-menu" : undefined}
        aria-haspopup="true"
        aria-expanded={open ? "true" : undefined}
        onClick={handleClick}
        endIcon={<ExpandMoreIcon />}
      >
        Tags
      </Button>
      <Popover
        open={open}
        anchorEl={anchorEl}
        onClose={handleClose}
        anchorOrigin={{
          vertical: "bottom",
          horizontal: "left",
        }}
      >
        <Paper sx={{ p: 2 }}>
          <Grid container spacing={2}>
            <Grid item lg={6}>
              <Typography>Selected Tags</Typography>
              {data.filters.tags.map((tag) => (
                <TagChip key={tag.id} tag={tag} />
              ))}
            </Grid>
            <Grid item lg={6}>
              <TextField
                label="Search tags"
                size="small"
                onChange={onSearchChange}
              />
              {searchedTags.map((tag) => (
                <MenuItem key={tag.id} sx={{ p: 0 }}>
                  <Stack direction="row" alignItems="center" spacing={1}>
                    <Checkbox
                      value={tag}
                      checked={selectedTags.includes(tag)}
                    />
                    <Typography onClick={() => onClick(tag)}>
                      {tag.name}
                    </Typography>
                    <Chip label={tag.submissionCount} size="small" />
                  </Stack>
                </MenuItem>
              ))}
            </Grid>
          </Grid>
        </Paper>
      </Popover>
    </div>
  );
}
