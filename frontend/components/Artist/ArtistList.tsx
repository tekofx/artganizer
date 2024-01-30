import { Typography } from "@mui/material";
import { useAppContext } from "../../pages/_app";
import ArtistLabel from "./ArtistLabel";
interface ArtistListProps {
  search?: string;
  clickable?: boolean;
}

export default function ArtistList({ search, clickable }: ArtistListProps) {
  const { artists } = useAppContext();

  return (
    <div
      style={{
        maxHeight: "35vh",
        overflowY: "auto",
      }}
    >
      {artists.length == 0 && <Typography>No artists</Typography>}
      {artists
        .filter((artist) =>
          artist.name.toLowerCase().includes(search === undefined ? "" : search)
        )
        .map((artist) => (
          <ArtistLabel key={artist.id} artist={artist} clickable={clickable} />
        ))}
    </div>
  );
}
