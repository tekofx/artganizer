import Search from "./Search";
import { Stack, Paper } from "@mui/material";
import TagFilter from "./Filters/TagFilter";
import RatingFilter from "./Filters/RatingFilter";
import ClearFilters from "./Filters/ClearFilters";
export default function TopPanel() {
  return (
    <Paper sx={{ width: "100%", p: 1 }}>
      <Stack direction="row">
        <Search />
        <RatingFilter />
        <TagFilter />
        <ClearFilters />
      </Stack>
    </Paper>
  );
}
