import { Button } from "@mui/material";
import { DataContext } from "../../../pages/_app";
import { useContext } from "react";
import ClearIcon from "@mui/icons-material/Clear";
export default function ClearFilters() {
  const { data, setData } = useContext(DataContext);
  var emptyFilters = {
    rating: -1,
    tags: [],
    folders: [],
    artist: undefined,
    title: "",
    characters: [],
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
    data.filters.artist === emptyFilters.artist &&
    data.filters.title === emptyFilters.title &&
    data.filters.characters.length === emptyFilters.characters.length;

  return (
    <div>
      {!filtersAreEmpty && (
        <Button onClick={() => clearFilters()} startIcon={<ClearIcon />}>
          Clear Filters
        </Button>
      )}
    </div>
  );
}
