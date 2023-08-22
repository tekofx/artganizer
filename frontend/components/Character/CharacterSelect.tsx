import { Dispatch, SetStateAction, useState, MouseEvent } from "react";
import {
  Paper,
  Typography,
  TextField,
  Popper,
  Stack,
  Grid,
} from "@mui/material";
import Character from "../../interfaces/Character";
import SelectableCharacterList from "./SelectableCharacterList";
import CharacterLabel from "./CharacterLabel";
interface CharacterSelectProps {
  setSelectedCharacters: Dispatch<SetStateAction<Character[]>>;
  selectedCharacters: Character[];
}
export default function CharacterSelect(props: CharacterSelectProps) {
  const [open, setOpen] = useState(false);
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);

  const [filter, setFilter] = useState<string>("");

  const handleClick = (event: MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
    if (!open) {
      setOpen((previousOpen) => !previousOpen);
    }
  };

  function removeCharacter(char: Character) {
    props.setSelectedCharacters(
      props.selectedCharacters.filter((t) => t.id !== char.id)
    );
  }

  return (
    <Grid container>
      <Grid item xs={12}>
        <Typography>Character select</Typography>
      </Grid>
      <Grid container spacing={1}>
        {props.selectedCharacters.map((char) => (
          <Grid item key={char.id}>
            <Stack direction="row" spacing={2} alignItems="center">
              <CharacterLabel
                character={char}
                onDelete={() => removeCharacter(char)}
              />
            </Stack>
          </Grid>
        ))}
      </Grid>

      {props.selectedCharacters.length < 1 && (
        <>
          <Typography>No characters selected</Typography>
        </>
      )}
      <Grid item xs={12}>
        <TextField
          label="Search characters"
          variant="standard"
          size="small"
          value={filter}
          onClick={handleClick}
          onChange={(event) => {
            setFilter(event.target.value);
          }}
        />
      </Grid>
      <Popper open={open} anchorEl={anchorEl} sx={{ zIndex: 2000 }}>
        <Paper sx={{ width: "200px" }}>
          <SelectableCharacterList
            selectedCharacters={props.selectedCharacters}
            setSelectedCharacters={props.setSelectedCharacters}
            setOpen={setOpen}
            filter={filter}
          />
        </Paper>
      </Popper>
    </Grid>
  );
}
