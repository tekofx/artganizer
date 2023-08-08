import Filters from "./Filters";
import Search from "./Search";
import { Stack, Paper } from "@mui/material";
export default function TopPanel() {
  return (
    <Paper sx={{ width: "100%" }}>
      <Stack direction="row">
        <Search />
        <Filters />
      </Stack>
    </Paper>
  );
}
