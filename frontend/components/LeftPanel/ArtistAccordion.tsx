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

function ArtistList() {
  const { data } = useContext(DataContext);
  const router = useRouter();

  return (
    <>
      {data.artists.length == 0 && <Typography>No artists</Typography>}
      {data.artists.map((artist) => (
        <MenuItem
          key={artist.id}
          onClick={() => router.push(`/artist/${artist.id}`)}
        >
          <Stack direction="row" spacing={1}>
            <PersonIcon />
            <Typography>{artist.name}</Typography>
          </Stack>
        </MenuItem>
      ))}
    </>
  );
}

export default function ArtistAccordion() {
  const [expanded, setExpanded] = useState<boolean>(true);

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
          <ArtistList />
        </Stack>
      </AccordionDetails>
    </Accordion>
  );
}
