import { Paper, Stack } from "@mui/material";

import { useAppContext } from "../../../../pages/_app";
import SearchBar from "../../../SearchBar";
import {
  ArtistFilter,
  CharacterFilter,
  ClearFilters,
  ColorFilter,
  RatingFilter,
  TagFilter,
} from "./Filters";
export default function TopPanel() {
  const { filters, setFilters } = useAppContext();
  function onChange(event: React.ChangeEvent<HTMLInputElement>) {
    event.preventDefault();
    setFilters({ ...filters, title: event.target.value });
    console.log(filters);
  }
  return (
    <Paper sx={{ width: "100%", p: 1, position: "sticky", top: 0, zIndex: 4000 }}>
      <Stack direction="row" spacing={1}>
        <SearchBar onChange={onChange} show />
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
