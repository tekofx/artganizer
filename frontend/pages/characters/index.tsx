import { Paper, Typography } from "@mui/material";
import Layout from "components/Layout";
import { useState } from "react";
import CharacterList from "../../components/Character/CharacterList";
import SearchBar from "../../components/SearchBar";

export default function ArtistsPage() {
  const [search, setSearch] = useState<string | undefined>(undefined);

  function onChange(event: React.ChangeEvent<HTMLInputElement>) {
    setSearch(event.target.value);
  }

  return (
    <Layout>
      <Paper sx={{
        p: 2, minHeight: "100vh",
        overflowY: "auto",
        maxHeight: "100vh",
      }}>
        <Typography variant="h1" align="center">
          Characters
        </Typography>
        <SearchBar onChange={onChange} show fullWidth />
        <CharacterList search={search} clickable />
      </Paper>
    </Layout>
  );
}
