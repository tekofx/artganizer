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
  Badge,
} from "@mui/material";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import { useState, MouseEvent, useEffect, Dispatch, SetStateAction } from "react";
import Tag from "../../../../interfaces/Tag";
import TagChip from "../../../Tag/TagChip";
import LocalOfferIcon from "@mui/icons-material/LocalOffer";
import { Filters } from "../../../../interfaces";
import axios from "axios";
export default function TagFilter({ filters, setFilters }: { filters: Filters, setFilters: Dispatch<SetStateAction<Filters>> }) {
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const [searchedTags, setSearchedTags] = useState<Tag[]>([]);
  const [invisible, setInvisible] = useState<boolean>(true);

  useEffect(() => {
    const getTags = async () => {
      const res = await axios.get(process.env.API_URL + "/tags");
      setSearchedTags(res.data);
      console.log(res.data);
    }
    getTags();
  }, []);

  const open = Boolean(anchorEl);

  const onSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    var temp = filters.tags.filter((tag) =>
      tag.name.toLowerCase().includes(event.target.value.toLowerCase())
    );
    setSearchedTags(temp);
  };

  const handleClick = (event: MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  function onClick(tag: Tag) {
    if (filters.tags.includes(tag)) {
      setFilters({ ...filters, tags: filters.tags.filter((t) => t != tag) });
      console.log(filters);
    } else {
      setFilters({ ...filters, tags: [...filters.tags, tag] });
    }
  }

  useEffect(() => {
    if (filters.tags.length == 0) {
      setInvisible(true);
    } else {
      setInvisible(false);
    }
  }, [filters.tags]);

  return (
    <div>
      <Button
        id="basic-button"
        aria-controls={open ? "basic-menu" : undefined}
        aria-haspopup="true"
        aria-expanded={open ? "true" : undefined}
        onClick={handleClick}
        endIcon={<ExpandMoreIcon />}
        startIcon={
          <Badge variant="dot" color="error" invisible={invisible}>
            <LocalOfferIcon />
          </Badge>
        }
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
              {filters.tags.map((tag) => (
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
                <MenuItem
                  key={tag.id}
                  sx={{ p: 0 }}
                  onClick={() => onClick(tag)}
                >
                  <Stack direction="row" alignItems="center" spacing={1}>
                    <Checkbox
                      value={tag}
                      checked={filters.tags.includes(tag)}
                    />
                    <Typography>{tag.name}</Typography>
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
