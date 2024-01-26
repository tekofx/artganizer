import { Autocomplete, Button, Paper, TextField } from "@mui/material";
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


    function handleDeleteCharacter(character: Character) {
        setSelectedCharacters(selectedCharacters.filter((t) => t.id !== character.id));
    }

    async function onAddCharacter() {
        var newCharacter: Character = { name: inputValue, description: "", id: -1, image: "" }
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
                <TextField {...params} variant="outlined" label="Characters" />
            )}
            renderOption={(props, character) => {
                const isSelected = selectedCharacters.some((selectedCharacter) => selectedCharacter.id === character.id);
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
                        <CharacterLabel character={character} onDelete={() => handleDeleteCharacter(character)} />
                    </Paper>
                ))
            }
            noOptionsText={<Button onClick={onAddCharacter}>Create character {inputValue}</Button>}
        />
    );
}
