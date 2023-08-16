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
  Grid,
} from "@mui/material";
import { DataContext } from "../../pages/_app";
import Artist from "../../interfaces/Artist";
import { useRouter } from "next/router";

interface ArtistListProps {
  selectedArtist: Artist | undefined;
  setSelectedArtist: Dispatch<SetStateAction<Artist | undefined>>;
  setOpen: Dispatch<SetStateAction<boolean>>;
  filter: string;
}

export function ArtistList(props: ArtistListProps) {
  const router = useRouter();
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
            <Stack direction="row" spacing={1}>
              <img loading="lazy" width="20" src={artist.image} alt="" />
              <Typography>{artist.name}</Typography>
            </Stack>
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
  const { data } = useContext(DataContext);
  const [open, setOpen] = useState(false);
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const [selectedArtist, setSelectedArtist] = useState<Artist | undefined>(
    props.selectedArtist
  );

  const [filter, setFilter] = useState<string>("");

  const handleClick = (event: MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
    if (!open) {
      setOpen((previousOpen) => !previousOpen);
    }
  };

  return (
    <Paper>
      <Typography>Artist select</Typography>
      <Stack direction="row" spacing={2}>
        {selectedArtist != undefined ? (
          <Grid container spacing={2}>
            <Grid item xs={2}>
              <img
                loading="lazy"
                width="20"
                src={selectedArtist.image}
                alt=""
              />
            </Grid>
            <Grid item xs={10}>
              <Typography>{selectedArtist.name}</Typography>
            </Grid>
          </Grid>
        ) : (
          // Aquí puedes agregar el componente que quieres mostrar cuando selectedArtist es undefined
          <Typography>No artist selected</Typography>
        )}

        <TextField
          label="Search artist"
          variant="standard"
          onClick={handleClick}
          size="small"
          value={filter}
          onChange={(event) => {
            setFilter(event.target.value);
          }}
        />
      </Stack>
      <Popper open={open} anchorEl={anchorEl}>
        <Paper sx={{ width: "100%" }}>
          <ArtistList
            selectedArtist={selectedArtist}
            setSelectedArtist={setSelectedArtist}
            setOpen={setOpen}
            filter={filter}
          />
        </Paper>
      </Popper>
    </Paper>
  );
}
