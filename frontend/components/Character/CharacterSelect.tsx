import { Dispatch, SetStateAction, useState } from "react";
import { Paper, TextField, Grid, Typography } from "@mui/material";
import Character from "../../interfaces/Character";
import SelectableCharacterList from "./SelectableCharacterList";
import CharacterLabel from "./CharacterLabel";

interface CharacterSelectProps {
  setSelectedCharacters: Dispatch<SetStateAction<Character[]>>;
  selectedCharacters: Character[];
}
export default function CharacterSelect({
  setSelectedCharacters,
  selectedCharacters,
}: CharacterSelectProps) {
  const [open, setOpen] = useState(false);

  const [filter, setFilter] = useState<string>("");

  const handleClick = () => {
    if (!open) {
      setOpen((previousOpen) => !previousOpen);
    }
  };

  function removeCharacter(char: Character) {
    setSelectedCharacters(selectedCharacters.filter((t) => t.id !== char.id));
  }

  return (
    <Paper elevation={0} sx={{ p: 1 }}>
      <Grid container spacing={1}>
        <Grid item xs={12}>
          <Typography>Characters</Typography>
        </Grid>
        <Grid item xs={12}>
          <Grid container spacing={1}>
            {selectedCharacters.map((char) => (
              <Grid item key={char.id}>
                <CharacterLabel
                  character={char}
                  onDelete={() => removeCharacter(char)}
                />
              </Grid>
            ))}
          </Grid>
        </Grid>

        {selectedCharacters.length < 1 && (
          <Grid item>
            <CharacterLabel />
          </Grid>
        )}
        <Grid item xs={12}>
          <TextField
            label="Search characters"
            variant="standard"
            fullWidth
            size="small"
            value={filter}
            onClick={handleClick}
            onChange={(event) => {
              setFilter(event.target.value);
            }}
          />
        </Grid>
        <Grid item xs={12} sx={{ display: open ? "block" : "none" }}>
          <Paper sx={{ width: "100%", overflowY: "auto", maxHeight: "200px" }}>
            <SelectableCharacterList
              selectedCharacters={selectedCharacters}
              setSelectedCharacters={setSelectedCharacters}
              setOpen={setOpen}
              filter={filter}
            />
          </Paper>
        </Grid>
      </Grid>
    </Paper>
  );
}
