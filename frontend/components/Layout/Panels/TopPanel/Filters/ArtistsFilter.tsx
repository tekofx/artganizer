import BrushIcon from "@mui/icons-material/Brush";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import { Badge, Button, Grid, Paper, Popover } from "@mui/material";
import { MouseEvent, useEffect, useState } from "react";
import Artist from "../../../../../interfaces/Artist";
import { useAppContext } from "../../../../../pages/_app";
import ArtistsAutocomplete from "../../../../Artist/ArtistsAutocomplete";

export default function ArtistFilter() {
  const { filters, setFilters } = useAppContext();
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const [artists, setArtists] = useState<Artist[]>([]);
  const [invisible, setInvisible] = useState<boolean>(true);

  const open = Boolean(anchorEl);

  const handleClick = (event: MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  useEffect(() => {
    if (filters.artists.length > 0) {
      setArtists(filters.artists);
      setInvisible(false);
    } else {
      setArtists([]);
      setInvisible(true);
    }
  }, [filters.artists]);

  useEffect(() => {
    var newFilter = { ...filters };
    newFilter.artists = artists;
    setFilters(newFilter);
  }, [artists]);

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
            <BrushIcon />
          </Badge>
        }
      >
        Artists
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
              <ArtistsAutocomplete
                selectedArtists={artists}
                setSelectedArtists={setArtists}
              />
            </Grid>
          </Grid>
        </Paper>
      </Popover>
    </div>
  );
}
