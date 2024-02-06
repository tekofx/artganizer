import { Paper, Typography } from "@mui/material";
import Layout from "components/Layout";
import { useState } from "react";
import SearchBar from "../../components/SearchBar";
import TagLabelList from "../../components/Tag/TagLabelList";

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
      }} >
        <Typography variant="h1" align="center">
          Tags
        </Typography>
        <SearchBar onChange={onChange} show fullWidth />
        <br />
        <Paper elevation={2} sx={{ p: 2 }}>

          <TagLabelList search={search} />
        </Paper>
      </Paper>
    </Layout>
  );
}
