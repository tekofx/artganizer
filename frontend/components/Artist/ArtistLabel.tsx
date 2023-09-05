import { Stack, Avatar, Typography, IconButton } from "@mui/material";
import Artist from "../../interfaces/Artist";
import { useRouter } from "next/router";
import { useState } from "react";
import ClearIcon from "@mui/icons-material/Clear";

interface ArtistLabelProps {
  artist?: Artist;
  clickable?: boolean;
  onDelete?: () => void;
}
export default function Page({
  artist,
  clickable,
  onDelete,
}: ArtistLabelProps) {
  const router = useRouter();
  const [highlight, setHighlight] = useState(false);

  function handleClick() {
    if (clickable) {
      router.push(`/artist/${artist?.id}`);
    }
  }

  function handleMouseEnter() {
    if (clickable) {
      setHighlight(true);
    }
  }

  function handleMouseLeave() {
    if (clickable) {
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
        cursor: clickable ? "pointer" : "default",
        p: 1,
        backgroundColor: highlight ? "grey" : "transparent",
      }}
    >
      {artist ? (
        <>
          <Avatar src={process.env.API_URL + `/artists/uploads/${artist?.id}`} />
          <Typography>{artist?.name}</Typography>
          {onDelete && (
            <IconButton onClick={onDelete}>
              <ClearIcon />
            </IconButton>
          )}
        </>
      ) : (
        <>
          <Avatar>
            <ClearIcon />
          </Avatar>
          <Typography>No artist selected</Typography>
        </>
      )}
    </Stack>
  );
}
