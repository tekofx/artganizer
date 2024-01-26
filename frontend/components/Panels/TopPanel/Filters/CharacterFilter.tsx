import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import PersonIcon from "@mui/icons-material/Person";
import { Badge, Button, Grid, Paper, Popover } from "@mui/material";
import { Dispatch, MouseEvent, SetStateAction, useEffect, useState } from "react";
import { Filters } from "../../../../interfaces";
import Character from "../../../../interfaces/Character";
import CharacterAutocomplete from "../../../Character/CharacterAutocomplete";
export default function CharacterFilter({ filters, setFilters }: { filters: Filters, setFilters: Dispatch<SetStateAction<Filters>> }) {
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const [characters, setCharacters] = useState<Character[]>([]);
  const [invisible, setInvisible] = useState<boolean>(true);

  const open = Boolean(anchorEl);

  const handleClick = (event: MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };


  useEffect(() => {
    console.log(filters.characters)
    if (filters.characters.length != 0) {
      setInvisible(false);
    } else {
      setInvisible(true);
    }
  }, [filters]);

  useEffect(() => {
    setFilters({ ...filters, characters: characters });
  }
    , [characters]);


  return (
    <div>
      <Button
        id="basic-button"
        aria-controls={open ? "basic-menu" : undefined}
        aria-haspopup="true"
        aria-expanded={open ? "true" : undefined}
        onClick={handleClick}
        endIcon={<ExpandMoreIcon />}
        startIcon={
          <Badge variant="dot" color="error" invisible={invisible}>
            <PersonIcon />
          </Badge>
        }
      >
        Characters
      </Button>
      <Popover
        open={open}
        anchorEl={anchorEl}
        onClose={handleClose}
        anchorOrigin={{
          vertical: "bottom",
          horizontal: "left",
        }}
      >
        <Paper sx={{ p: 2 }}>
          <Grid container spacing={2}>
            <Grid item lg={12}>
              <CharacterAutocomplete
                selectedCharacters={characters}
                setSelectedCharacters={setCharacters}
              />
            </Grid>
          </Grid>
        </Paper>
      </Popover>
    </div>
  );
}
