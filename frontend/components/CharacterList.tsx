import { useRouter } from "next/router";
import { Typography, Stack, MenuItem, Icon } from "@mui/material";
import LocalOfferIcon from "@mui/icons-material/LocalOffer";
import Character from "../interfaces/Character";

interface CharacterListProps {
  characters: Character[] | undefined;
}

export default function CharacterList(props: CharacterListProps) {
  const router = useRouter();

  return (
    <>
      {props.characters == undefined && <Typography>No characters</Typography>}
      {props.characters?.length == 0 && <Typography>No characters</Typography>}
      {props.characters?.map((character) => (
        <MenuItem
          key={character.id}
          onClick={() => router.push(`/character/${character.id}`)}
        >
          <Stack direction="row" spacing={1}>
            <Typography>{character.name}</Typography>
          </Stack>
        </MenuItem>
      ))}
    </>
  );
}
