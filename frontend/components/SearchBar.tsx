import { TextField, IconButton, Stack, Tooltip } from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";
import { useState } from "react";

interface Props {
  onChange: any;
}
export default function SearchBar({ onChange }: Props) {
  const [search, setSearch] = useState("");
  const [showSearchBar, setShowSearchBar] = useState(false);

  return (
    <Stack direction="row">
      <TextField
        id="outlined-basic"
        label="Search"
        variant="outlined"
        size="small"
        value={search}
        onChange={(event) => {
          setSearch(event.target.value);
          onChange(event);
        }}
        sx={{ display: showSearchBar ? "flex" : "none" }}
      />
      <Tooltip title="Search">
        <IconButton onClick={() => setShowSearchBar(!showSearchBar)}>
          <SearchIcon />
        </IconButton>
      </Tooltip>
    </Stack>
  );
}
