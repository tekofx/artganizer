import { Stack, Avatar, Typography } from "@mui/material";
import Artist from "../../interfaces/Artist";
import { useRouter } from "next/router";
import { useState } from "react";
interface ArtistLabelProps {
  artist: Artist;
  clickable?: boolean;
}
export default function Page(props: ArtistLabelProps) {
  const router = useRouter();
  const [highlight, setHighlight] = useState(false);

  function handleClick() {
    if (props.clickable) {
      router.push(`/artist/${props.artist.id}`);
    }
  }

  function handleMouseEnter() {
    if (props.clickable) {
      setHighlight(true);
    }
  }

  function handleMouseLeave() {
    if (props.clickable) {
      setHighlight(false);
    }
  }

  return (
    <Stack
      direction="row"
      alignItems="center"
      spacing={2}
      onClick={handleClick}
      onMouseEnter={handleMouseEnter}
      onMouseLeave={handleMouseLeave}
      sx={{
        cursor: props.clickable ? "pointer" : "default",
        p: 1,
        backgroundColor: highlight ? "grey" : "transparent",
      }}
    >
      <Avatar
        src={`http://localhost:3001/artists/uploads/${props.artist.id}`}
      />
      <Typography>{props.artist.name}</Typography>
    </Stack>
  );
}
