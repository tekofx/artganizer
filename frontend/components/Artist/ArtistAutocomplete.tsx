import { Autocomplete, Button, Paper, TextField } from "@mui/material";
import axios from "axios";
import { Dispatch, SetStateAction, useEffect, useState } from "react";
import { Artist } from "../../interfaces";
import ArtistLabel from "./ArtistLabel";
interface TagListProps {
    selectedArtist: Artist | undefined;
    setSelectedArtist: Dispatch<SetStateAction<Artist | undefined>>;
}


export default function ArtistAutocomplete({
    selectedArtist,
    setSelectedArtist,
}: TagListProps) {
    const [inputValue, setInputValue] = useState("");
    const [artists, setArtists] = useState<Artist[]>([]);
    const [selectedArtists, setSelectedArtists] = useState<Artist[]>([]); // Needed to make autocomplete with multiple work
    const getArtists = async () => {
        var res = await axios.get(process.env.API_URL + "/artists");
        setArtists(res.data);
    };
    useEffect(() => {
        getArtists();
    }, []);

    function handleDeleteArtist(artist: Artist) {
        setSelectedArtists([]);
        setSelectedArtist(artist);

    }

    async function onAddArtist() {
        await axios.post(process.env.API_URL + `/characters`, { name: inputValue, description: "" }).then((res) => {
            setSelectedArtist(selectedArtist);
            setInputValue("");
            getArtists();
        }
        );
    }

    return (
        <Autocomplete
            sx={{ minWidth: "20rem" }}
            multiple
            options={artists}
            getOptionLabel={(artist) => artist.name}
            value={selectedArtists}
            inputValue={inputValue}
            onInputChange={(event, newInputValue) => {
                setInputValue(newInputValue);
            }}
            onChange={(event, newValue) => {
                const latestValue = newValue[newValue.length - 1];
                setSelectedArtists([latestValue]);
                setSelectedArtist(latestValue);
            }}
            renderInput={(params) => {
                return (
                    <TextField
                        {...params}
                        variant="outlined"
                        label="Artist"
                    />
                );
            }}
            // Render elements in list
            renderOption={(props, artist) => {
                return (
                    <li {...props}>
                        <ArtistLabel artist={artist} />
                    </li>
                );
            }}

            // Render selected elements
            renderTags={(value) =>
                value.map((artist) => (
                    <Paper key={artist.id} sx={{ borderRadius: "20" }}>
                        <ArtistLabel artist={artist} onDelete={() => handleDeleteArtist(artist)} />
                    </Paper>
                ))
            }
            noOptionsText={<Button onClick={onAddArtist}>Create character {inputValue}</Button>}
        />
    );
}
