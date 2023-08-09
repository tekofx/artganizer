import { useRouter } from "next/router";
import { Typography, Stack, MenuItem } from "@mui/material";
import Artist from "../interfaces/Artist";
interface ArtistListProps {
  artist: Artist | undefined;
}

export default function ArtistList(props: ArtistListProps) {
  const router = useRouter();

  return (
    <>
      {props.artist == undefined && <Typography>No artists</Typography>}
      {props.artist != undefined && (
        <MenuItem
          key={props.artist?.id}
          onClick={() => router.push(`/artist/${props.artist?.id}`)}
        >
          <Stack direction="row" spacing={1}>
            <Typography>{props.artist?.name}</Typography>
          </Stack>
        </MenuItem>
      )}
    </>
  );
}
