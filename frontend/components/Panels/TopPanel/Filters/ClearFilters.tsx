import { Button } from "@mui/material";
import { DataContext } from "../../../../pages/_app";
import { Dispatch, SetStateAction, useContext, useEffect, useState } from "react";
import ClearIcon from "@mui/icons-material/Clear";
import { Filters } from "../../../../interfaces";
const emptyFilters: Filters = {
  rating: -1,
  tags: [],
  artist: undefined,
  title: "",
  characters: [],
  color: "",
};
export default function ClearFilters({ filters, setFilters }: { filters: Filters, setFilters: Dispatch<SetStateAction<Filters>> }) {
  const [show, setShow] = useState<boolean>(false);

  function clearFilters() {
    setFilters(emptyFilters);
  }

  useEffect(() => {
    const filtersAreEmpty =
      filters.rating === emptyFilters.rating &&
      filters.tags.length === emptyFilters.tags.length &&
      filters.artist === emptyFilters.artist &&
      filters.title === emptyFilters.title &&
      filters.color === emptyFilters.color &&
      filters.characters.length === emptyFilters.characters.length;

    if (filtersAreEmpty) {
      setShow(false);
    } else {
      setShow(true);
    }
  }, [filters]);

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
