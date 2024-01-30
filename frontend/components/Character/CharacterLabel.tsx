import ClearIcon from "@mui/icons-material/Clear";
import { Avatar, IconButton, Stack, Typography } from "@mui/material";
import { useRouter } from "next/router";
import { useState } from "react";
import Character from "../../interfaces/Character";
interface CharacterLabelProps {
  character?: Character;
  clickable?: boolean;
  onDelete?: () => void;
}
export default function Page({
  character,
  clickable,
  onDelete,
}: CharacterLabelProps) {
  const router = useRouter();
  const [highlight, setHighlight] = useState(false);

  function handleClick() {
    if (clickable) {
      router.push(`/characters/${character?.id}`);
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
      {character ? (
        <>
          <Avatar src={character.image} />
          <Typography>{character?.name}</Typography>
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
          <Typography>No characters selected</Typography>
        </>
      )}
    </Stack>
  );
}
