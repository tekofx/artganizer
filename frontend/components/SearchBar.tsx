import { TextField } from "@mui/material";
import { useState } from "react";

interface Props {
  onChange: any;
}
export default function SearchBar({ onChange }: Props) {
  const [search, setSearch] = useState("");

  return (
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
    />
  );
}
