import AddIcon from "@mui/icons-material/Add";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import PersonIcon from "@mui/icons-material/Person";
import SearchIcon from "@mui/icons-material/Search";
import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Grid,
  IconButton,
  Stack,
  Typography,
} from "@mui/material";
import { useState } from "react";
import { Character } from "../../../../interfaces";
import { useAppContext } from "../../../../pages/_app";
import CharacterList from "../../../Character/CharacterList";
import CharacterForm from "../../../Forms/CharacterForm";
import SearchBar from "../../../SearchBar";
export default function CharacterAccordion() {
  const { characters } = useAppContext();
  const [charactersList, setCharactersList] = useState<Character[]>([]);
  const [expanded, setExpanded] = useState<boolean>(true);
  const [showSearchBar, setShowSearchBar] = useState<boolean>(false);
  const [open, setOpen] = useState<boolean>(false);


  function onChange(event: React.ChangeEvent<HTMLInputElement>) {
    var temp = characters.filter((character) =>
      character.name.toLowerCase().includes(event.target.value.toLowerCase())
    );
    setCharactersList(temp);
  }

  function onSearchIconClick() {
    if (!expanded) setExpanded(!expanded);

    if (!showSearchBar) {
      setShowSearchBar(true);
    } else {
      setShowSearchBar(false);
      setCharactersList(characters);
    }
  }

  return (
    <Accordion expanded={expanded}>
      <AccordionSummary
        expandIcon={<ExpandMoreIcon onClick={() => setExpanded(!expanded)} />}
        aria-controls="panel1a-content"
        id="panel1a-header"
        sx={{ flexDirection: "row-reverse", justifyContent: "space-between" }}
      >
        <Grid container alignItems="center">
          <Grid item lg={8}>
            <Stack
              direction="row"
              spacing={1}
              alignItems="center"
              justifyContent="center"
            >
              <PersonIcon />
              <Typography onClick={() => setExpanded(!expanded)}>
                Characters
              </Typography>
            </Stack>
          </Grid>
          <Grid item lg={4}>
            <Stack direction="row">
              <IconButton onClick={() => setOpen(true)}>
                <AddIcon />
              </IconButton>
              <IconButton onClick={onSearchIconClick}>
                <SearchIcon />
              </IconButton>
            </Stack>
          </Grid>
        </Grid>
      </AccordionSummary>
      <AccordionDetails>
        <Stack direction="column">
          <SearchBar
            fullWidth
            onChange={onChange}
            show={showSearchBar}
            focus={showSearchBar}
          />
          <CharacterList characters={charactersList.length == 0 ? characters : charactersList} clickable />
        </Stack>
      </AccordionDetails>
      <CharacterForm open={open} setOpen={setOpen} />
    </Accordion>
  );
}
