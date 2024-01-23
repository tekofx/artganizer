import { Dispatch, SetStateAction, useState, useEffect } from "react";
import { Typography, MenuItem, Button } from "@mui/material";
import CharacterLabel from "./CharacterLabel";
import CharacterForm from "../Forms/CharacterForm";
import AddIcon from "@mui/icons-material/Add";
import Character from "../../interfaces/Character";
import axios from "axios";
interface CharacterListProps {
  selectedCharacters: Character[];
  setSelectedCharacters: Dispatch<SetStateAction<Character[]>>;
  setOpen: Dispatch<SetStateAction<boolean>>;
  filter: string;
}

export default function SelectableCharacterList(props: CharacterListProps) {
  const [openCharacterForm, setOpenCharacterForm] = useState(false);
  const [characters, setCharacters] = useState<Character[]>([]);

  useEffect(() => {
    const getCharacters = async () => {
      var res = await axios.get(process.env.API_URL + "/characters");
      setCharacters(res.data);
    }
    getCharacters();
  }
    , []);

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
      {characters.filter((artist) =>
        artist.name.toLowerCase().includes(props.filter.toLowerCase())
      ).length == 0 && <Typography>No characters</Typography>}
      {characters

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

      {
        // If no character with name filter exists, show a button to create a new character
        characters.filter((artist) =>
          artist.name.toLowerCase().includes(props.filter.toLowerCase())
        ).length == 0 && (
          <Button onClick={() => setOpenCharacterForm(true)}>
            <AddIcon />
            Create new character
          </Button>
        )
      }
      <Button onClick={() => props.setOpen(false)}>Close</Button>
      <CharacterForm
        open={openCharacterForm}
        setOpen={setOpenCharacterForm}
        name={props.filter}
      />
    </>
  );
}
