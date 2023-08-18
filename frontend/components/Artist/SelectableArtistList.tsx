import { useContext, Dispatch, SetStateAction } from "react";
import { Typography, MenuItem } from "@mui/material";
import { DataContext } from "../../pages/_app";
import Artist from "../../interfaces/Artist";
import ArtistLabel from "./ArtistLabel";
interface ArtistListProps {
  selectedArtist: Artist | undefined;
  setSelectedArtist: Dispatch<SetStateAction<Artist | undefined>>;
  setOpen: Dispatch<SetStateAction<boolean>>;
  filter: string;
}

export default function SelectableArtistList(props: ArtistListProps) {
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
