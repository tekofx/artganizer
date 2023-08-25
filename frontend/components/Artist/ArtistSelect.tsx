import { Dispatch, SetStateAction, useState, MouseEvent } from "react";
import {
  Paper,
  Typography,
  TextField,
  Popper,
  Stack,
  IconButton,
} from "@mui/material";
import Artist from "../../interfaces/Artist";
import ClearIcon from "@mui/icons-material/Clear";
import ArtistLabel from "./ArtistLabel";
import SelectableArtistList from "./SelectableArtistList";

interface ArtistSelectProps {
  setSelectedArtist: Dispatch<SetStateAction<Artist | undefined>>;
  selectedArtist: Artist | undefined;
}
export default function ArtistSelect(props: ArtistSelectProps) {
  const [open, setOpen] = useState(false);
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
    <>
      <Typography>Artist select</Typography>
      <Stack direction="row" spacing={2} alignItems="center">
        {props.selectedArtist != undefined ? (
          <Stack direction="row" spacing={2} alignItems="center">
            <ArtistLabel artist={props.selectedArtist} />
            <IconButton onClick={onRemove}>
              <ClearIcon />
            </IconButton>
          </Stack>
        ) : (
          <>
            <Typography>No artist selected</Typography>
            <TextField
              label="Search artist"
              variant="standard"
              size="small"
              value={filter}
              onClick={handleClick}
              onChange={(event) => {
                setFilter(event.target.value);
              }}
            />
          </>
        )}
      </Stack>
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
    </>
  );
}
