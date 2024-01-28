import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import LocalOfferIcon from "@mui/icons-material/LocalOffer";
import {
  Badge,
  Button,
  Checkbox,
  Chip,
  Grid,
  MenuItem,
  Paper,
  Popover,
  Stack,
  TextField,
  Typography,
} from "@mui/material";
import { MouseEvent, useEffect, useState } from "react";
import Tag from "../../../../interfaces/Tag";
import { useAppContext } from "../../../../pages/_app";
import TagChip from "../../../Tag/TagChip";
export default function TagFilter() {
  const { filters, setFilters, tags } = useAppContext();
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const [searchedTags, setSearchedTags] = useState<Tag[]>(tags);
  const [invisible, setInvisible] = useState<boolean>(true);

  useEffect(() => {
    if (filters.tags.length == 0) {
      setInvisible(true);
    } else {
      setInvisible(false);
    }
  }, [filters]);
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
