import { Typography } from "@mui/material";
import Character from "../../interfaces/Character";
import CharacterLabel from "./CharacterLabel";
interface CharacterListProps {
  characters: Character[] | undefined;
  clickable?: boolean;
}

export default function CharacterList(props: CharacterListProps) {
  return (
    <div
      style={{
        maxHeight: "35vh",
        overflowY: "auto",
      }}
    >
      {props.characters?.length == 0 && <Typography>No characters</Typography>}
      {props.characters?.map((character) => (
        <CharacterLabel key={character.id} character={character} clickable />
      ))}
    </div>
  );
}
