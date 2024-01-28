import AddIcon from "@mui/icons-material/Add";
import BrushIcon from "@mui/icons-material/Brush";
import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Grid,
  IconButton,
  Stack,
  Typography,
} from "@mui/material";

import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import SearchIcon from "@mui/icons-material/Search";
import { useEffect, useState } from "react";
import { ArtistForm } from "../../../Forms";
import SearchBar from "../../../SearchBar";

import { Artist } from "../../../../interfaces";
import { useAppContext } from "../../../../pages/_app";
import ArtistList from "../../../Artist/ArtistList";

export default function ArtistAccordion() {
  const { artists } = useAppContext();
  const [artistsList, setArtistsList] = useState<Artist[]>([]);
  const [showSearchBar, setShowSearchBar] = useState<boolean>(false);
  const [expanded, setExpanded] = useState<boolean>(true);
  const [open, setOpen] = useState<boolean>(false);

  function onChange(event: React.ChangeEvent<HTMLInputElement>) {
    var temp = artists.filter((artist) =>
      artist.name.toLowerCase().includes(event.target.value.toLowerCase())
    );
    setArtistsList(temp);
  }
  function onSearchIconClick() {
    if (!expanded) setExpanded(!expanded);

    if (!showSearchBar) {
      setShowSearchBar(true);
    } else {
      setShowSearchBar(false);
      setArtistsList(artists);
    }
  }

  useEffect(() => {
    console.log(artists);
  }, [artists]);

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
          <ArtistList
            artists={artistsList.length == 0 ? artists : artistsList}
            clickable
          />
        </Stack>
      </AccordionDetails>
      <ArtistForm open={open} setOpen={setOpen} />
    </Accordion>
  );
}
