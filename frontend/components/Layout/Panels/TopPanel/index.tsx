import { Grid, Paper } from "@mui/material";

import { useAppContext } from "../../../../pages/_app";
import SearchBar from "../../../SearchBar";
import {
  ArtistFilter,
  CharacterFilter,
  ClearFilters,
  ColorFilter,
  RatingFilter,
  TagFilter
} from "./Filters";
export default function TopPanel() {
  const { filters, setFilters, isMobile } = useAppContext();
  function onChange(event: React.ChangeEvent<HTMLInputElement>) {
    event.preventDefault();
    setFilters({ ...filters, title: event.target.value });
  }
  return (
    <>
      {!isMobile ?
        (

          <Paper
            sx={{
              width: "100%",
              p: 1,
              position: "sticky",
              top: 0,
              zIndex: 4000,
              display: "block",
            }}
          >
            <Grid container spacing={1}>
              <Grid item >
                <SearchBar onChange={onChange} show />
              </Grid>
              <Grid item>
                <RatingFilter />
              </Grid>
              <Grid item>
                <TagFilter />
              </Grid>
              <Grid item>
                <ColorFilter />
              </Grid>
              <Grid item>
                <ArtistFilter />
              </Grid>
              <Grid item>
                <CharacterFilter />
              </Grid>
              <Grid item>
                <ClearFilters />
              </Grid>
            </Grid>
          </Paper>
        ) :
        null
      }

    </>

  );
}
