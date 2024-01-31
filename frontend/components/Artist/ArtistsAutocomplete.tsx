import {
    Autocomplete,
    Button,
    Paper,
    TextField,
    Typography,
} from "@mui/material";
import { Dispatch, SetStateAction, useState } from "react";
import { Artist } from "../../interfaces";
import { useAppContext } from "../../pages/_app";
import ArtistLabel from "./ArtistLabel";
interface TagListProps {
    selectedArtists: Artist[];
    setSelectedArtists: Dispatch<SetStateAction<Artist[]>>;
}

export default function ArtistAutocomplete({
    setSelectedArtists,
    selectedArtists
}: TagListProps) {
    const { artists, createArtist } = useAppContext();

    const [inputValue, setInputValue] = useState("");

    function handleDeleteArtist(artist: Artist) {
        var newArtists = selectedArtists.filter(
            (selectedArtist) => selectedArtist.id != artist.id
        );
        setSelectedArtists(newArtists);
    }

    async function onAddArtist() {
        if (inputValue == "") {
            return;
        }

        var newArtist: Artist = {
            name: inputValue,
            description: "",
            id: -1,
            socials: [],
            submissions: [],
            image: "",
        };
        var result = await createArtist(newArtist);
        if (result) {
            var newArtists = [...selectedArtists, result]
            setSelectedArtists(newArtists);
            setInputValue("");
        }
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
                setSelectedArtists(newValue);
            }}
            renderInput={(params) => {
                return (
                    <TextField
                        {...params}
                        variant="outlined"
                        label="Artist"
                        onKeyDown={(event) => {
                            if (event.key === "Tab") {
                                event.preventDefault();
                                const artistExists = artists.some(
                                    (artist) => artist.name === inputValue
                                );
                                if (!artistExists) {
                                    onAddArtist();
                                }
                            }
                        }}
                    />
                );
            }}
            // Render elements in list
            renderOption={(props, artist) => {
                const isSelected = selectedArtists.some(
                    (selectedArtist) => selectedArtist.id === artist.id
                );
                if (isSelected) {
                    props["aria-selected"] = true;
                }
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
                        <ArtistLabel
                            artist={artist}
                            onDelete={() => handleDeleteArtist(artist)}
                        />
                    </Paper>
                ))
            }
            noOptionsText={
                inputValue != "" ? (
                    <Button onClick={onAddArtist}>
                        Tab to Create artist {inputValue}
                    </Button>
                ) : (
                    <Typography>Type a name to create an Artist</Typography>
                )
            }
        />
    );
}
