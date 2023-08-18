import { Typography, MenuItem } from "@mui/material";
import Artist from "../../interfaces/Artist";
import ArtistLabel from "./ArtistLabel";
import { useRouter } from "next/router";
interface ArtistListProps {
  artists: Artist[];
}

export default function ArtistList(props: ArtistListProps) {
  const router = useRouter();
  return (
    <>
      {props.artists.length == 0 && <Typography>No artists</Typography>}
      {props.artists.map((artist) => (
        <MenuItem
          key={artist.id}
          onClick={() => router.push(`/artist/${artist.id}`)}
        >
          <ArtistLabel artist={artist} />
        </MenuItem>
      ))}
    </>
  );
}
