import { Button, Paper, Popover, Grid, Badge } from "@mui/material";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import { useState, MouseEvent, useContext, useEffect } from "react";
import { DataContext } from "../../../pages/_app";
import ArtistSelect from "../../Artist/ArtistSelect";
import Artist from "../../../interfaces/Artist";
import BrushIcon from "@mui/icons-material/Brush";

export default function ArtistFilter() {
  const { data, setData } = useContext(DataContext);
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
      var newData = { ...data };
      newData.filters.artist = artist;
      setData(newData);
    } else {
      setInvisible(true);
      var newData = { ...data };
      newData.filters.artist = undefined;
      setData(newData);
    }
  }, [artist]);

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
