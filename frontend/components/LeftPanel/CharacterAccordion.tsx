import {
  Stack,
  Typography,
  Accordion,
  AccordionSummary,
  AccordionDetails,
} from "@mui/material";
import { useState, useContext } from "react";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import { DataContext } from "../../pages/_app";
import SearchBar from "../SearchBar";

import CharacterList from "../Character/CharacterList";
export default function ArtistAccordion() {
  const { data } = useContext(DataContext);
  const [characters, setCharacters] = useState(data.characters);
  const [expanded, setExpanded] = useState<boolean>(true);

  function onChange(event: React.ChangeEvent<HTMLInputElement>) {
    var temp = data.characters.filter((character) =>
      character.name.toLowerCase().includes(event.target.value.toLowerCase())
    );
    setCharacters(temp);
  }

  return (
    <Accordion expanded={expanded}>
      <AccordionSummary
        expandIcon={<ExpandMoreIcon onClick={() => setExpanded(!expanded)} />}
        aria-controls="panel1a-content"
        id="panel1a-header"
        sx={{ flexDirection: "row-reverse", justifyContent: "space-between" }}
      >
        <Stack
          direction="row"
          justifyContent="space-between"
          alignItems="center"
          width="100%"
          spacing={2}
        >
          <Typography onClick={() => setExpanded(!expanded)}>
            Characters
          </Typography>
          <SearchBar onChange={onChange} />
        </Stack>
      </AccordionSummary>
      <AccordionDetails>
        <Stack direction="column">
          <CharacterList characters={characters} clickable />
        </Stack>
      </AccordionDetails>
    </Accordion>
  );
}
