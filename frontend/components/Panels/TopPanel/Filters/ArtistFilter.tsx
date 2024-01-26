import BrushIcon from "@mui/icons-material/Brush";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import { Badge, Button, Grid, Paper, Popover } from "@mui/material";
import { MouseEvent, useEffect, useState } from "react";
import { Filters } from "../../../../interfaces";
import Artist from "../../../../interfaces/Artist";
import ArtistAutocomplete from "../../../Artist/ArtistAutocomplete";

export default function ArtistFilter({ filters }: { filters: Filters }) {
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
    if (filters.artist != undefined) {
      setArtist(filters.artist);
      setInvisible(false);
    } else {
      setArtist(undefined);
      setInvisible(true);
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
              <ArtistAutocomplete
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
