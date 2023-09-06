import { Button } from "@mui/material";
import { DataContext } from "../../../../pages/_app";
import { useContext, useEffect, useState } from "react";
import ClearIcon from "@mui/icons-material/Clear";
import { Filters } from "../../../../interfaces";
const emptyFilters: Filters = {
  rating: -1,
  tags: [],
  folders: [],
  artist: undefined,
  title: "",
  characters: [],
  color: "",
};
export default function ClearFilters() {
  const { data, setData } = useContext(DataContext);
  const [show, setShow] = useState<boolean>(false);

  function clearFilters() {
    var newData = { ...data };
    newData.filters = emptyFilters;
    setData(newData);
  }

  useEffect(() => {
    const filtersAreEmpty =
      data.filters.rating === emptyFilters.rating &&
      data.filters.tags.length === emptyFilters.tags.length &&
      data.filters.folders.length === emptyFilters.folders.length &&
      data.filters.artist === emptyFilters.artist &&
      data.filters.title === emptyFilters.title &&
      data.filters.color === emptyFilters.color &&
      data.filters.characters.length === emptyFilters.characters.length;

    if (filtersAreEmpty) {
      setShow(false);
    } else {
      setShow(true);
    }
  }, [data.filters]);

  return (
    <div>
      {show && (
        <Button
          onClick={() => clearFilters()}
          startIcon={<ClearIcon />}
          variant="outlined"
        >
          Clear Filters
        </Button>
      )}
    </div>
  );
}
