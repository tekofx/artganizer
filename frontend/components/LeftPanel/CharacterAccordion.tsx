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

import CharacterList from "../Character/CharacterList";
export default function ArtistAccordion() {
  const [expanded, setExpanded] = useState<boolean>(true);
  const { data } = useContext(DataContext);

  return (
    <Accordion expanded={expanded}>
      <AccordionSummary
        expandIcon={<ExpandMoreIcon onClick={() => setExpanded(!expanded)} />}
        aria-controls="panel1a-content"
        id="panel1a-header"
        sx={{ flexDirection: "row-reverse", justifyContent: "space-between" }}
      >
        <Typography onClick={() => setExpanded(!expanded)}>
          Characters
        </Typography>
      </AccordionSummary>
      <AccordionDetails>
        <Stack direction="column">
          <CharacterList characters={data.characters} clickable />
        </Stack>
      </AccordionDetails>
    </Accordion>
  );
}
