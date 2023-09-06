import { Stack, Paper } from "@mui/material";
import { DataContext } from "../../../pages/_app";
import { useContext } from "react";

import {
  RatingFilter,
  ColorFilter,
  ClearFilters,
  TagFilter,
  ArtistFilter,
  CharacterFilter,
} from "./Filters";
import SearchBar from "../../SearchBar";
export default function TopPanel() {
  const { data, setData } = useContext(DataContext);

  function onChange(event: React.ChangeEvent<HTMLInputElement>) {
    var newData = { ...data };
    newData.filters.title = event.target.value;
    setData(newData);
  }
  return (
    <Paper sx={{ width: "100%", p: 1, position: "sticky", top: 0 }}>
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
