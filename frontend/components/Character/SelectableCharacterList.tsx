import { useContext, Dispatch, SetStateAction } from "react";
import { Typography, MenuItem, Button } from "@mui/material";
import { DataContext } from "../../pages/_app";
import CharacterLabel from "./CharacterLabel";
import Character from "../../interfaces/Character";
interface CharacterListProps {
  selectedCharacters: Character[];
  setSelectedCharacters: Dispatch<SetStateAction<Character[]>>;
  setOpen: Dispatch<SetStateAction<boolean>>;
  filter: string;
}

export default function SelectableCharacterList(props: CharacterListProps) {
  const { data } = useContext(DataContext);

  function handleClick(char: Character) {
    if (props.selectedCharacters.includes(char)) {
      props.setSelectedCharacters(
        props.selectedCharacters.filter((t) => t.id !== char.id)
      );
      return;
    }

    props.setSelectedCharacters([...props.selectedCharacters, char]);
  }

  function charInSelectedCharacters(char: Character) {
    // Check by character id
    return props.selectedCharacters.some((t) => t.id === char.id);
  }

  return (
    <>
      {data.characters.filter((artist) =>
        artist.name.toLowerCase().includes(props.filter.toLowerCase())
      ).length == 0 && <Typography>No characters</Typography>}
      {data.characters

        .filter((removeCharacter) =>
          removeCharacter.name
            .toLowerCase()
            .includes(props.filter.toLowerCase())
        )

        .map((removeCharacter) => (
          <MenuItem
            key={removeCharacter.id}
            selected={charInSelectedCharacters(removeCharacter)}
            onClick={() => handleClick(removeCharacter)}
          >
            <CharacterLabel character={removeCharacter} />
          </MenuItem>
        ))}
      <Button onClick={() => props.setOpen(false)}>Close</Button>
    </>
  );
}
