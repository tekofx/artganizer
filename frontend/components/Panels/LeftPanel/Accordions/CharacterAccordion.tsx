import {
  Stack,
  Typography,
  Accordion,
  AccordionSummary,
  AccordionDetails,
  Grid,
  IconButton,
} from "@mui/material";
import { useState, useContext } from "react";
import AddIcon from "@mui/icons-material/Add";

import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import { DataContext } from "../../../../pages/_app";
import SearchBar from "../../../SearchBar";
import SearchIcon from "@mui/icons-material/Search";
import CharacterList from "../../../Character/CharacterList";
import PersonIcon from "@mui/icons-material/Person";
import CharacterForm from "../../../Forms/CharacterForm";
export default function ArtistAccordion() {
  const { data } = useContext(DataContext);
  const [characters, setCharacters] = useState(data.characters);
  const [expanded, setExpanded] = useState<boolean>(true);
  const [showSearchBar, setShowSearchBar] = useState<boolean>(false);
  const [open, setOpen] = useState<boolean>(false);

  function onChange(event: React.ChangeEvent<HTMLInputElement>) {
    var temp = data.characters.filter((character) =>
      character.name.toLowerCase().includes(event.target.value.toLowerCase())
    );
    setCharacters(temp);
  }

  function onSearchIconClick() {
    if (!expanded) setExpanded(!expanded);

    if (!showSearchBar) {
      setShowSearchBar(true);
    } else {
      setShowSearchBar(false);
      setCharacters(data.characters);
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
          <CharacterList characters={characters} clickable />
        </Stack>
      </AccordionDetails>
      <CharacterForm open={open} setOpen={setOpen} />
    </Accordion>
  );
}
