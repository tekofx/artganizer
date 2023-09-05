import { Dispatch, SetStateAction, useState, MouseEvent } from "react";
import {
  Paper,
  TextField,
  Popper,
  IconButton,
  Grid,
  Tooltip,
} from "@mui/material";
import Artist from "../../interfaces/Artist";
import ArtistLabel from "./ArtistLabel";
import SelectableArtistList from "./SelectableArtistList";
import AddIcon from "@mui/icons-material/Add";
import { ArtistForm } from "../Forms";

interface ArtistSelectProps {
  setSelectedArtist: Dispatch<SetStateAction<Artist | undefined>>;
  selectedArtist: Artist | undefined;
}
export default function ArtistSelect(props: ArtistSelectProps) {
  const [open, setOpen] = useState(false);
  const [openArtistForm, setOpenArtistForm] = useState(false);
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);

  const [filter, setFilter] = useState<string>("");

  const handleClick = (event: MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
    if (!open) {
      setOpen((previousOpen) => !previousOpen);
    }
  };

  function onRemove() {
    props.setSelectedArtist(undefined);
  }

  return (
    <Grid container spacing={1}>
      <Grid item xs={12}>
        <Grid container spacing={1}>
          {props.selectedArtist != undefined && (
            <Grid item>
              <ArtistLabel artist={props.selectedArtist} onDelete={onRemove} />
            </Grid>
          )}

          {props.selectedArtist == undefined && (
            <>
              <Grid item xs={12}>
                <ArtistLabel />
              </Grid>
              <Grid item xs={10}>
                <TextField
                  fullWidth
                  label="Search artist"
                  variant="standard"
                  size="small"
                  value={filter}
                  onClick={handleClick}
                  onChange={(event) => {
                    setFilter(event.target.value);
                  }}
                />
              </Grid>
              <Grid item xs={2}>
                <Tooltip title="Create new artist">
                  <IconButton onClick={() => setOpenArtistForm(true)}>
                    <AddIcon />
                  </IconButton>
                </Tooltip>
              </Grid>
            </>
          )}
        </Grid>
      </Grid>
      <Popper open={open} anchorEl={anchorEl} sx={{ zIndex: 2000 }}>
        <Paper sx={{ width: "200px" }}>
          <SelectableArtistList
            selectedArtist={props.selectedArtist}
            setSelectedArtist={props.setSelectedArtist}
            setOpen={setOpen}
            filter={filter}
          />
        </Paper>
      </Popper>
      <ArtistForm open={openArtistForm} setOpen={setOpenArtistForm} />
    </Grid>
  );
}
