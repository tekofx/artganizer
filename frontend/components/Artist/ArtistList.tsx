import { Typography } from "@mui/material";
import Artist from "../../interfaces/Artist";
import ArtistLabel from "./ArtistLabel";
interface ArtistListProps {
  artists: Artist[];
}

export default function ArtistList(props: ArtistListProps) {
  return (
    <>
      {props.artists.length == 0 && <Typography>No artists</Typography>}
      {props.artists.map((artist) => (
        <ArtistLabel key={artist.id} artist={artist} clickable />
      ))}
    </>
  );
}
