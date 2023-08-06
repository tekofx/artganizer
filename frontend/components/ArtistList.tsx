import { useRouter } from "next/router";
import { Typography, Stack, MenuItem, Icon } from "@mui/material";
import LocalOfferIcon from "@mui/icons-material/LocalOffer";
import Artist from "../interfaces/Artist";
interface ArtistListProps {
  artists: Artist[] | undefined;
}

export default function CharacterList(props: ArtistListProps) {
  const router = useRouter();

  return (
    <>
      {props.artists == undefined && <Typography>No artists</Typography>}
      {props.artists?.length == 0 && <Typography>No artists</Typography>}
      {props.artists?.map((artist) => (
        <MenuItem
          key={artist.id}
          onClick={() => router.push(`/artist/${artist.id}`)}
        >
          <Stack direction="row" spacing={1}>
            <Typography>{artist.name}</Typography>
          </Stack>
        </MenuItem>
      ))}
    </>
  );
}
