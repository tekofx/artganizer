import { Dispatch, SetStateAction, useState, MouseEvent } from "react";
import {
  Paper,
  Typography,
  TextField,
  Popper,
  Stack,
  Grid,
  Avatar,
  IconButton,
} from "@mui/material";
import CharacterForm from "../Forms/CharacterForm";
import Character from "../../interfaces/Character";
import ClearIcon from "@mui/icons-material/Clear";
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
    <Grid container spacing={1}>
      <Grid item xs={12}>
        <Typography>Character select</Typography>
      </Grid>
      <Grid item xs={12}>
        <Grid container spacing={1}>
          {selectedCharacters.map((char) => (
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
      </Grid>

      {selectedCharacters.length < 1 && (
        <Grid item>
          <Stack direction="row" alignItems="center" spacing={2}>
            <Avatar>
              <ClearIcon />
            </Avatar>
            <Typography>No characters selected</Typography>
          </Stack>
        </Grid>
      )}
      <Grid item xs={10}>
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
      <Grid item xs={2}>
        <IconButton onClick={() => setOpenCharacterForm(true)}>
          <AddIcon />
        </IconButton>
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
      <CharacterForm open={openCharacterForm} setOpen={setOpenCharacterForm} />
    </Grid>
  );
}
