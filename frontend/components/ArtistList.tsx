import { useRouter } from "next/router";
import { Typography, Stack, MenuItem, Icon } from "@mui/material";
import LocalOfferIcon from "@mui/icons-material/LocalOffer";
import Artist from "../interfaces/Artist";
import { PinDropSharp } from "@mui/icons-material";
interface ArtistListProps {
  artist: Artist | undefined;
}

export default function ArtistList(props: ArtistListProps) {
  const router = useRouter();

  return (
    <>
      {props.artist == undefined && <Typography>No artists</Typography>}
      <MenuItem
        key={props.artist?.id}
        onClick={() => router.push(`/artist/${props.artist?.id}`)}
      >
        <Stack direction="row" spacing={1}>
          <Typography>{props.artist?.name}</Typography>
        </Stack>
      </MenuItem>
    </>
  );
}
