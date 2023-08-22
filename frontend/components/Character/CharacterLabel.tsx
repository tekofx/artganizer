import { Stack, Avatar, Typography } from "@mui/material";
import { useRouter } from "next/router";
import { useState } from "react";
import Character from "../../interfaces/Character";
interface CharacterLabelProps {
  character: Character;
  clickable?: boolean;
}
export default function Page(props: CharacterLabelProps) {
  const router = useRouter();
  const [highlight, setHighlight] = useState(false);

  function handleClick() {
    if (props.clickable) {
      router.push(`/character/${props.character.id}`);
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
        src={`http://localhost:3001/characters/uploads/${props.character.id}`}
      />
      <Typography>{props.character.name}</Typography>
    </Stack>
  );
}
