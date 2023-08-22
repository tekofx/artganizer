import { Dispatch, SetStateAction, useState, MouseEvent } from "react";
import {
  Paper,
  Typography,
  TextField,
  Popper,
  Stack,
  IconButton,
} from "@mui/material";
import Artist from "../../interfaces/Artist";
import ClearIcon from "@mui/icons-material/Clear";
import CharacterLabel from "./CharacterLabel";
import SelectableCharacterList from "./SelectableCharacterList";
import Character from "../../interfaces/Character";

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

  return (
    <>
      <Typography>Character select</Typography>
      <Stack direction="row" spacing={2} alignItems="center">
        {props.selectedCharacters.length > 0 ? (
          <Stack direction="row" spacing={2} alignItems="center">
            <CharacterLabel character={props.selectedCharacter} />
            <IconButton
              onClick={() => {
                props.setSelectedCharacter(undefined);
              }}
            >
              <ClearIcon />
            </IconButton>
          </Stack>
        ) : (
          <>
            <Typography>No artist selected</Typography>
            <TextField
              label="Search artist"
              variant="standard"
              size="small"
              value={filter}
              onClick={handleClick}
              onChange={(event) => {
                setFilter(event.target.value);
              }}
            />
          </>
        )}
      </Stack>
      <Popper open={open} anchorEl={anchorEl} sx={{ zIndex: 2000 }}>
        <Paper sx={{ width: "200px" }}>
          <SelectableCharacterList
            selectedCharacters={props.selectedCharacter}
            setSelectedCharacter={props.setSelectedCharacter}
            setOpen={setOpen}
            filter={filter}
          />
        </Paper>
      </Popper>
    </>
  );
}
