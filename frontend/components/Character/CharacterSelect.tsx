import { Dispatch, SetStateAction, useState, MouseEvent } from "react";
import {
  Paper,
  TextField,
  Popper,
  Grid,
  IconButton,
  Tooltip,
  Typography,
} from "@mui/material";
import CharacterForm from "../Forms/CharacterForm";
import Character from "../../interfaces/Character";
import SelectableCharacterList from "./SelectableCharacterList";
import CharacterLabel from "./CharacterLabel";
import AddIcon from "@mui/icons-material/Add";

interface CharacterSelectProps {
  setSelectedCharacters: Dispatch<SetStateAction<Character[]>>;
  selectedCharacters: Character[];
}
export default function CharacterSelect({
  setSelectedCharacters,
  selectedCharacters,
}: CharacterSelectProps) {
  const [open, setOpen] = useState(false);
  const [openCharacterForm, setOpenCharacterForm] = useState(false);
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);

  const [filter, setFilter] = useState<string>("");

  const handleClick = (event: MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
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
        <Grid item xs={10}>
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
        <Grid item xs={2}>
          <Tooltip title="Create new character">
            <IconButton onClick={() => setOpenCharacterForm(true)}>
              <AddIcon />
            </IconButton>
          </Tooltip>
        </Grid>
        <Popper open={open} anchorEl={anchorEl} sx={{ zIndex: 2000 }}>
          <Paper sx={{ width: "200px" }}>
            <SelectableCharacterList
              selectedCharacters={selectedCharacters}
              setSelectedCharacters={setSelectedCharacters}
              setOpen={setOpen}
              filter={filter}
            />
          </Paper>
        </Popper>
        <CharacterForm
          open={openCharacterForm}
          setOpen={setOpenCharacterForm}
        />
      </Grid>
    </Paper>
  );
}
