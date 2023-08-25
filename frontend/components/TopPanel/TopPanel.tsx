import Search from "./Search";
import { Stack, Paper } from "@mui/material";
import {
  RatingFilter,
  ColorFilter,
  ClearFilters,
  TagFilter,
  ArtistFilter,
  CharacterFilter,
} from "./Filters";
export default function TopPanel() {
  return (
    <Paper sx={{ width: "100%", p: 1, position: "sticky", top: 0 }}>
      <Stack direction="row" spacing={3}>
        <Search />
        <RatingFilter />
        <TagFilter />
        <ColorFilter />
        <ArtistFilter />
        <CharacterFilter />
        <ClearFilters />
      </Stack>
    </Paper>
  );
}
