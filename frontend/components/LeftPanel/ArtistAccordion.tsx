import {
  Stack,
  MenuItem,
  Typography,
  Accordion,
  AccordionSummary,
  AccordionDetails,
} from "@mui/material";
import { useState, useContext } from "react";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import PersonIcon from "@mui/icons-material/Person";
import { useRouter } from "next/router";
import { DataContext } from "../../pages/_app";

import ArtistList from "../Artist/ArtistList";
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
        <Typography onClick={() => setExpanded(!expanded)}>Artists</Typography>
      </AccordionSummary>
      <AccordionDetails>
        <Stack direction="column">
          <ArtistList artists={data.artists} />
        </Stack>
      </AccordionDetails>
    </Accordion>
  );
}
