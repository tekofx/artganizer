import { Stack, Avatar, Typography, MenuItem } from "@mui/material";
import Artist from "../../interfaces/Artist";
import { useRouter } from "next/router";
interface ArtistLabelProps {
  artist: Artist;
  clickable?: boolean;
}
export default function Page(props: ArtistLabelProps) {
  const router = useRouter();

  function handleClick() {
    if (props.clickable) {
      router.push(`/artist/${props.artist.id}`);
    }
  }
  return (
    <MenuItem
      onClick={handleClick}
      sx={{ cursor: props.clickable ? "pointer" : "default" }}
    >
      <Stack direction="row" alignItems="center" spacing={2}>
        <Avatar
          src={`http://localhost:3001/artists/uploads/${props.artist.id}`}
        />
        <Typography>{props.artist.name}</Typography>
      </Stack>
    </MenuItem>
  );
}
