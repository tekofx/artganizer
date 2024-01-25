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
import AddIcon from "@mui/icons-material/Add";

import { useState, useEffect } from "react";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import SearchBar from "../../../SearchBar";
import SearchIcon from "@mui/icons-material/Search";
import { ArtistForm } from "../../../Forms";

import ArtistList from "../../../Artist/ArtistList";
import { Artist } from "../../../../interfaces";
import axios from "axios";
export default function ArtistAccordion() {
  const [artists, setArtists] = useState<Artist[]>([]);
  const [showSearchBar, setShowSearchBar] = useState<boolean>(false);
  const [expanded, setExpanded] = useState<boolean>(true);
  const [open, setOpen] = useState<boolean>(false);

  function onChange(event: React.ChangeEvent<HTMLInputElement>) {
    var temp = artists.filter((artist) =>
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
      setArtists(artists);
    }
  }

  useEffect(() => {
    const getArtists = async () => {
      var res = await axios.get(process.env.API_URL + "/artists");
      setArtists(res.data);
      console.log(res.data);
    }
    getArtists();

  }, []);

  return (
    <Accordion expanded={expanded}>
      <AccordionSummary
        expandIcon={<ExpandMoreIcon onClick={() => setExpanded(!expanded)} />}
        sx={{ height: "auto", maxHeight: "auto" }}
      >
        <Grid container alignItems="center">
          <Grid item lg={8}>
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
          <ArtistList artists={artists} clickable />
        </Stack>
      </AccordionDetails>
      <ArtistForm open={open} setOpen={setOpen} />
    </Accordion>
  );
}
