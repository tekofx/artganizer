import { Typography } from "@mui/material";
import Artist from "../../interfaces/Artist";
import ArtistLabel from "./ArtistLabel";
interface ArtistListProps {
  artists: Artist[];
  clickable?: boolean;
}

function sortByName(artists: Artist[]): Artist[] {
  return artists.sort(function (a, b) {
    var textA = a.name.toUpperCase();
    var textB = b.name.toUpperCase();

    return textA < textB ? -1 : textA > textB ? 1 : 0;
  });
}

export default function ArtistList({ artists, clickable }: ArtistListProps) {
  artists = sortByName(artists);
  return (
    <div
      style={{
        maxHeight: "35vh",
        overflowY: "auto",
      }}
    >
      {artists.length == 0 && <Typography>No artists</Typography>}
      {artists.map((artist) => (
        <ArtistLabel key={artist.id} artist={artist} clickable={clickable} />
      ))}
    </div>
  );
}
