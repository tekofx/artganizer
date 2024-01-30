import { Paper, Typography } from "@mui/material";
import { useState } from "react";
import SearchBar from "../../components/SearchBar";
import TagLabelList from "../../components/Tag/TagLabelList";

export default function ArtistsPage() {
  const [search, setSearch] = useState<string | undefined>(undefined);

  function onChange(event: React.ChangeEvent<HTMLInputElement>) {
    setSearch(event.target.value);
  }

  return (
    <Paper sx={{ p: 2 }}>
      <Typography variant="h1" align="center">
        Characters
      </Typography>
      <SearchBar onChange={onChange} show fullWidth />
      <br />
      <TagLabelList search={search} />
    </Paper>
  );
}
