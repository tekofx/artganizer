import { Paper, Typography } from "@mui/material";
import { useState } from "react";
import ArtistList from "../../components/Artist/ArtistList";
import SearchBar from "../../components/SearchBar";

export default function ArtistsPage() {
  const [search, setSearch] = useState<string | undefined>(undefined);

  function onChange(event: React.ChangeEvent<HTMLInputElement>) {
    setSearch(event.target.value);
  }

  return (
    <Paper sx={{
      p: 2, minHeight: "100vh",
      overflowY: "auto",
      maxHeight: "100vh",
    }}>
      <Typography variant="h1" align="center">
        Artists
      </Typography>
      <SearchBar onChange={onChange} show fullWidth />
      <ArtistList search={search} clickable />
    </Paper>
  );
}
