import {
  Stack,
  Typography,
  Accordion,
  AccordionSummary,
  AccordionDetails,
  IconButton,
  Grid,
} from "@mui/material";
import BrushIcon from "@mui/icons-material/Brush";
import { useState, useContext, useEffect } from "react";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import { DataContext } from "../../../pages/_app";
import SearchBar from "../SearchBar";
import SearchIcon from "@mui/icons-material/Search";

import ArtistList from "../../Artist/ArtistList";
export default function ArtistAccordion() {
  const { data } = useContext(DataContext);
  const [artists, setArtists] = useState(data.artists);
  const [showSearchBar, setShowSearchBar] = useState<boolean>(false);
  const [expanded, setExpanded] = useState<boolean>(true);

  function onChange(event: React.ChangeEvent<HTMLInputElement>) {
    var temp = data.artists.filter((artist) =>
      artist.name.toLowerCase().includes(event.target.value.toLowerCase())
    );
    setArtists(temp);
  }
  function onSearchIconClick() {
    if (!expanded) setExpanded(!expanded);

    if (!showSearchBar) {
      setShowSearchBar(true);
    } else {
      setShowSearchBar(false);
      setArtists(data.artists);
    }
  }

  useEffect(() => {
    setArtists(data.artists);
  }, [data.artists]);

  return (
    <Accordion expanded={expanded}>
      <AccordionSummary
        expandIcon={<ExpandMoreIcon onClick={() => setExpanded(!expanded)} />}
        sx={{ height: "auto", maxHeight: "auto" }}
      >
        <Grid container alignItems="center">
          <Grid item lg={10}>
            <Stack
              direction="row"
              spacing={1}
              alignItems="center"
              justifyContent="center"
            >
              <BrushIcon />
              <Typography onClick={() => setExpanded(!expanded)}>
                Artists
              </Typography>
            </Stack>
          </Grid>
          <Grid item lg={2}>
            <IconButton onClick={onSearchIconClick}>
              <SearchIcon />
            </IconButton>
          </Grid>
        </Grid>
      </AccordionSummary>
      <AccordionDetails>
        <Stack direction="column">
          <SearchBar onChange={onChange} show={showSearchBar} />
          <ArtistList artists={artists} clickable />
        </Stack>
      </AccordionDetails>
    </Accordion>
  );
}
