import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import LocalOfferIcon from "@mui/icons-material/LocalOffer";
import { Badge, Button, Grid, Paper, Popover } from "@mui/material";
import { MouseEvent, useEffect, useState } from "react";
import { Tag } from "../../../../../interfaces";
import { useAppContext } from "../../../../../pages/_app";
import TagAutocomplete from "../../../../Tag/TagAutocomplete";

export default function ArtistFilter() {
  const { filters, setFilters } = useAppContext();
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const [tags, setTags] = useState<Tag[]>([]);
  const [invisible, setInvisible] = useState<boolean>(true);

  const open = Boolean(anchorEl);

  const handleClick = (event: MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  useEffect(() => {
    if (filters.tags.length == 0) {
      setInvisible(true);
    } else {
      setInvisible(false);
    }
  }, [filters.tags]);

  useEffect(() => {
    var newFilter = { ...filters };
    newFilter.tags = tags;
    setFilters(newFilter);
  }, [tags]);

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
            <Grid item lg={12}>
              <TagAutocomplete selectedTags={tags} setSelectedTags={setTags} />
            </Grid>
          </Grid>
        </Paper>
      </Popover>
    </div>
  );
}
