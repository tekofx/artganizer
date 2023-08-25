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

import ArtistList from "../Artist/ArtistList";
export default function ArtistAccordion() {
  const { data } = useContext(DataContext);
  const [artists, setArtists] = useState(data.artists);
  const [expanded, setExpanded] = useState<boolean>(true);

  function onChange(event: React.ChangeEvent<HTMLInputElement>) {
    var temp = data.artists.filter((artist) =>
      artist.name.toLowerCase().includes(event.target.value.toLowerCase())
    );
    setArtists(temp);
  }

  return (
    <Accordion expanded={expanded}>
      <AccordionSummary
        expandIcon={<ExpandMoreIcon onClick={() => setExpanded(!expanded)} />}
        aria-controls="panel1a-content"
        id="panel1a-header"
        sx={{ flexDirection: "row-reverse" }}
      >
        <Stack
          direction="row"
          justifyContent="space-between"
          alignItems="center"
          width="100%"
          spacing={2}
        >
          <Typography onClick={() => setExpanded(!expanded)}>
            Artists
          </Typography>
          <SearchBar onChange={onChange} />
        </Stack>
      </AccordionSummary>
      <AccordionDetails>
        <Stack direction="column">
          <ArtistList artists={artists} clickable />
        </Stack>
      </AccordionDetails>
    </Accordion>
  );
}
