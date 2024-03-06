import {
  Autocomplete,
  Button,
  Paper,
  Popper,
  TextField,
  Typography,
} from "@mui/material";
import { Dispatch, SetStateAction, useState } from "react";
import { Artist } from "../../interfaces";
import { useAppContext } from "../../pages/_app";
import ArtistLabel from "./ArtistLabel";
interface TagListProps {
  selectedArtist: Artist | undefined;
  setSelectedArtist: Dispatch<SetStateAction<Artist | undefined>>;
}

export default function ArtistAutocomplete({
  setSelectedArtist,
}: TagListProps) {
  const { artists, createArtist } = useAppContext();

  const [inputValue, setInputValue] = useState("");
  const [selectedArtists, setSelectedArtists] = useState<Artist[]>([]); // Needed to make autocomplete with multiple work
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
  function handleDeleteArtist(artist: Artist) {
    setSelectedArtists([]);
    setSelectedArtist(artist);
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
      setSelectedArtists([result]);
      setSelectedArtist(result);
      setInputValue("");
    }
  }

  return (
    <Autocomplete
      sx={{ minWidth: "20rem" }}
      multiple
      options={artists}
      getOptionLabel={(artist) => artist.name}
      PopperComponent={CustomPopper}
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
