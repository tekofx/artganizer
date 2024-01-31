import ClearIcon from "@mui/icons-material/Clear";
import { Button } from "@mui/material";
import { useEffect, useState } from "react";
import { Filters } from "../../../../../interfaces";
import { useAppContext } from "../../../../../pages/_app";
const emptyFilters: Filters = {
  rating: -1,
  tags: [],
  artists: [],
  title: "",
  characters: [],
  color: "",
};
export default function ClearFilters() {
  const [show, setShow] = useState<boolean>(false);
  const { filters, setFilters } = useAppContext();

  function clearFilters() {
    setFilters(emptyFilters);
  }

  useEffect(() => {
    const filtersAreEmpty =
      filters.rating === emptyFilters.rating &&
      filters.tags.length === emptyFilters.tags.length &&
      filters.artists === emptyFilters.artists &&
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
