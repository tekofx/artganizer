import {
  Autocomplete,
  Button,
  Paper,
  Popper,
  TextField,
  Typography,
} from "@mui/material";
import { Dispatch, SetStateAction, useState } from "react";
import { Character } from "../../interfaces";
import { useAppContext } from "../../pages/_app";
import CharacterLabel from "./CharacterLabel";
interface TagListProps {
  selectedCharacters: Character[];
  setSelectedCharacters: Dispatch<SetStateAction<Character[]>>;
}

export default function CharacterAutocomplete({
  selectedCharacters,
  setSelectedCharacters,
}: TagListProps) {
  const { characters, createCharacter } = useAppContext();
  const [inputValue, setInputValue] = useState("");
  const CustomPopper = (props: any) => (
    <Popper
      {...props}
      style={{ zIndex: 10000 }}
      placement="bottom-start"
      modifiers={[
        {
          name: "matchWidth",
          enabled: true,
          phase: "beforeWrite",
          requires: ["computeStyles"],
          fn: ({ state }) => {
            state.styles.popper.width = `${state.rects.reference.width}px`;
          },
        },
      ]}
    />
  );

  function handleDeleteCharacter(character: Character) {
    setSelectedCharacters(
      selectedCharacters.filter((t) => t.id !== character.id)
    );
  }

  async function onAddCharacter() {
    if (inputValue == "") {
      return;
    }
    var newCharacter: Character = {
      name: inputValue,
      description: "",
      id: -1,
      image: "",
    };
    var result = await createCharacter(newCharacter);
    if (result) {
      setSelectedCharacters([...selectedCharacters, result]);
      setInputValue("");
    }
  }

  return (
    <Autocomplete
      multiple
      sx={{ minWidth: "20rem" }}
      PopperComponent={CustomPopper}
      options={characters}
      getOptionLabel={(character) => character.name}
      value={selectedCharacters}
      inputValue={inputValue}
      onInputChange={(event, newInputValue) => {
        setInputValue(newInputValue);
      }}
      onChange={(event, newValue) => {
        setSelectedCharacters(newValue);
      }}
      renderInput={(params) => (
        <TextField
          {...params}
          variant="outlined"
          label="Characters"
          onKeyDown={(event) => {
            if (event.key === "Tab") {
              event.preventDefault();
              const characterExists = characters.some(
                (character) => character.name === inputValue
              );
              if (!characterExists) {
                onAddCharacter();
              }
            }
          }}
        />
      )}
      renderOption={(props, character) => {
        const isSelected = selectedCharacters.some(
          (selectedCharacter) => selectedCharacter.id === character.id
        );
        if (isSelected) {
          props["aria-selected"] = true;
        }
        return (
          <li {...props}>
            <CharacterLabel character={character} />
          </li>
        );
      }}
      renderTags={(value) =>
        value.map((character) => (
          <Paper key={character.id} sx={{ borderRadius: "20" }}>
            <CharacterLabel
              character={character}
              onDelete={() => handleDeleteCharacter(character)}
            />
          </Paper>
        ))
      }
      noOptionsText={
        inputValue != "" ? (
          <Button onClick={onAddCharacter}>
            Tab to Create character {inputValue}
          </Button>
        ) : (
          <Typography>Type a name to create a Character</Typography>
        )
      }
    />
  );
}
