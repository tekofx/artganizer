import { Stack, Paper } from "@mui/material";
import { Dispatch, SetStateAction } from "react";

import {
  RatingFilter,
  ColorFilter,
  ClearFilters,
  TagFilter,
  ArtistFilter,
  CharacterFilter,
} from "./Filters";
import SearchBar from "../../SearchBar";
import { Filters } from "../../../interfaces";
export default function TopPanel({ filters, setFilters }: { filters: Filters, setFilters: Dispatch<SetStateAction<Filters>> }) {

  function onChange(event: React.ChangeEvent<HTMLInputElement>) {
    /* var newData = { ...data };
    newData.filters.title = event.target.value;
    setData(newData); */
    event.preventDefault();
    setFilters({ ...filters, title: event.target.value });
    console.log(filters);
  }
  return (
    <Paper sx={{ width: "100%", p: 1, position: "sticky", top: 0 }}>
      <Stack direction="row" spacing={1}>
        <SearchBar onChange={onChange} show />
        <RatingFilter filters={filters} setFilters={setFilters} />
        <TagFilter filters={filters} setFilters={setFilters} />
        <ColorFilter filters={filters} setFilters={setFilters} />
        <ArtistFilter filters={filters} setFilters={setFilters} />
        <CharacterFilter filters={filters} setFilters={setFilters} />
        <ClearFilters filters={filters} setFilters={setFilters} />
      </Stack>
    </Paper>
  );
}
