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
import { useState, MouseEvent, useContext, useEffect } from "react";
import { DataContext } from "../../../../pages/_app";
import Tag from "../../../../interfaces/Tag";
import TagChip from "../../../Tag/TagChip";
import LocalOfferIcon from "@mui/icons-material/LocalOffer";
export default function TagFilter() {
  const { data, setData } = useContext(DataContext);
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const [searchedTags, setSearchedTags] = useState<Tag[]>(data.tags);
  const [invisible, setInvisible] = useState<boolean>(true);

  const open = Boolean(anchorEl);

  const onSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    var temp = data.tags.filter((tag) =>
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
    if (data.filters.tags.includes(tag)) {
      setData((prevData) => ({
        ...prevData,
        filters: {
          ...prevData.filters,
          tags: prevData.filters.tags.filter((t) => t.id !== tag.id),
        },
      }));
    } else {
      setData((prevData) => ({
        ...prevData,
        filters: {
          ...prevData.filters,
          tags: [...prevData.filters.tags, tag],
        },
      }));
    }
  }

  useEffect(() => {
    if (data.filters.tags.length == 0) {
      setInvisible(true);
    } else {
      setInvisible(false);
    }
  }, [data.filters.tags]);

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
                <MenuItem
                  key={tag.id}
                  sx={{ p: 0 }}
                  onClick={() => onClick(tag)}
                >
                  <Stack direction="row" alignItems="center" spacing={1}>
                    <Checkbox
                      value={tag}
                      checked={data.filters.tags.includes(tag)}
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
