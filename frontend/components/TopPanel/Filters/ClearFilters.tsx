import { Paper, Button } from "@mui/material";
import { DataContext } from "../../../pages/_app";
import { useState, MouseEvent, useContext } from "react";

export default function ClearFilters() {
  const { data, setData } = useContext(DataContext);
  var emptyFilters = {
    rating: -1,
    tags: [],
    folders: [],
    artists: [],
  };

  function clearFilters() {
    var newData = { ...data };
    newData.filters = emptyFilters;
    setData(newData);
  }
const filtersAreEmpty =
  data.filters.rating === emptyFilters.rating &&
  data.filters.tags.length === emptyFilters.tags.length &&
  data.filters.folders.length === emptyFilters.folders.length &&
  data.filters.artists.length === emptyFilters.artists.length;

  return (
    <div>
      {!filtersAreEmpty && (
        <Button onClick={() => clearFilters()}>Clear Filters</Button>
      )}
    </div>
  );
}
