import {
  useContext,
  Dispatch,
  SetStateAction,
  useState,
  MouseEvent,
} from "react";
import {
  Paper,
  Typography,
  TextField,
  Popper,
  MenuItem,
  Stack,
  IconButton,
} from "@mui/material";
import { DataContext } from "../../pages/_app";
import Artist from "../../interfaces/Artist";
import ClearIcon from "@mui/icons-material/Clear";
import ArtistLabel from "./ArtistLabel";
interface ArtistListProps {
  selectedArtist: Artist | undefined;
  setSelectedArtist: Dispatch<SetStateAction<Artist | undefined>>;
  setOpen: Dispatch<SetStateAction<boolean>>;
  filter: string;
}

export function ArtistList(props: ArtistListProps) {
  const { data } = useContext(DataContext);

  function handleClick(artist: Artist) {
    props.setSelectedArtist(artist);
    props.setOpen(false);
  }

  return (
    <>
      {data.artists.filter((artist) =>
        artist.name.toLowerCase().includes(props.filter.toLowerCase())
      ).length == 0 && <Typography>No artists</Typography>}
      {data.artists

        .filter((artist) =>
          artist.name.toLowerCase().includes(props.filter.toLowerCase())
        )

        .map((artist) => (
          <MenuItem
            key={artist.id}
            selected={props.selectedArtist?.id == artist.id}
            onClick={() => handleClick(artist)}
          >
            <ArtistLabel artist={artist} />
          </MenuItem>
        ))}
    </>
  );
}

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

  return (
    <>
      <Typography>Artist select</Typography>
      <Stack direction="row" spacing={2} alignItems="center">
        {props.selectedArtist != undefined ? (
          <Stack direction="row" spacing={2} alignItems="center">
            <ArtistLabel artist={props.selectedArtist} />
            <IconButton
              onClick={() => {
                props.setSelectedArtist(undefined);
              }}
            >
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
      <Popper open={open} anchorEl={anchorEl}>
        <Paper sx={{ width: "200px" }}>
          <ArtistList
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
