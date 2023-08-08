import Search from "./Search";
import { Stack, Paper } from "@mui/material";
import TagFilter from "./Filters/TagFilter";
import RatingFilter from "./Filters/RatingFilter";
export default function TopPanel() {
  return (
    <Paper sx={{ width: "100%", p: 1 }}>
      <Stack direction="row">
        <Search />
        <RatingFilter />
        <TagFilter />
      </Stack>
    </Paper>
  );
}
