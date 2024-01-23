import { Button, Paper, Popover, Grid, Badge } from "@mui/material";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import { useState, MouseEvent, useEffect, Dispatch, SetStateAction } from "react";
import ArtistSelect from "../../../Artist/ArtistSelect";
import Artist from "../../../../interfaces/Artist";
import BrushIcon from "@mui/icons-material/Brush";
import { Filters } from "../../../../interfaces";

export default function ArtistFilter({ filters, setFilters }: { filters: Filters, setFilters: Dispatch<SetStateAction<Filters>> }) {
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const [artist, setArtist] = useState<Artist>();
  const [invisible, setInvisible] = useState<boolean>(true);

  const open = Boolean(anchorEl);

  const handleClick = (event: MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  useEffect(() => {
    if (artist != undefined) {
      setInvisible(false);
      setFilters({ ...filters, artist: artist });
    } else {
      setInvisible(true);
      setFilters({ ...filters, artist: undefined });
    }
  }, [artist]);

  useEffect(() => {
    if (filters.artist != undefined) {
      setArtist(filters.artist);
    } else {
      setArtist(undefined);
    }
  }, [filters.artist]);

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
        Artist
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
              <ArtistSelect
                selectedArtist={artist}
                setSelectedArtist={setArtist}
              />
            </Grid>
          </Grid>
        </Paper>
      </Popover>
    </div>
  );
}
