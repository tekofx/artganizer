import Search from "./Search";
import { Stack, Paper } from "@mui/material";
import TagFilter from "./Filters/TagFilter";
import RatingFilter from "./Filters/RatingFilter";
import ClearFilters from "./Filters/ClearFilters";
import ColorFilter from "./Filters/ColorFilter";
export default function TopPanel() {
  return (
    <Paper sx={{ width: "100%", p: 1 }}>
      <Stack direction="row">
        <Search />
        <RatingFilter />
        <TagFilter />
        <ColorFilter />
        <ClearFilters />
      </Stack>
    </Paper>
  );
}
