import { Typography } from "@mui/material";
import Artist from "../../interfaces/Artist";
import ArtistLabel from "./ArtistLabel";
interface ArtistListProps {
  artists: Artist[];
  clickable?: boolean;
}

export default function ArtistList({ artists, clickable }: ArtistListProps) {
  return (
    <>
      {artists.length == 0 && <Typography>No artists</Typography>}
      {artists.map((artist) => (
        <ArtistLabel key={artist.id} artist={artist} clickable={clickable} />
      ))}
    </>
  );
}
