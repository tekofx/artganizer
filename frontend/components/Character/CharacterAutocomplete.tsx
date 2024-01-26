import { Dispatch, SetStateAction, useEffect, useState } from "react";
import { TextField, Autocomplete, Button, Paper } from "@mui/material";
import axios from "axios";
import { Character } from "../../interfaces";
import CharacterLabel from "./CharacterLabel";
interface TagListProps {
    selectedCharacters: Character[];
    setSelectedCharacters: Dispatch<SetStateAction<Character[]>>;
}


export default function CharacterAutocomplete({
    selectedCharacters,
    setSelectedCharacters,
}: TagListProps) {
    const [inputValue, setInputValue] = useState("");
    const [characters, setCharacters] = useState<Character[]>([]);
    const getCharacters = async () => {
        var res = await axios.get(process.env.API_URL + "/characters");
        setCharacters(res.data);
    };
    useEffect(() => {
        getCharacters();
    }, []);

    function handleDeleteCharacter(character: Character) {
        setSelectedCharacters(selectedCharacters.filter((t) => t.id !== character.id));
    }

    async function onAddCharacter() {
        await axios.post(process.env.API_URL + `/characters`, { name: inputValue, description: "" }).then((res) => {
            setSelectedCharacters([...selectedCharacters, res.data]);
            setInputValue("");
            getCharacters();
        }
        );
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
