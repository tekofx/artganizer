import { TextField } from "@mui/material";
import { useState, useEffect, useRef } from "react";

interface Props {
  onChange: any;
  show?: boolean;
}
export default function SearchBar({ onChange, show }: Props) {
  const [search, setSearch] = useState("");
  const inputRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    if (show) {
      inputRef.current?.focus();
    }
  }, [show]);
  return (
    <TextField
      inputRef={inputRef}
      id="outlined-basic"
      label="Search"
      variant="outlined"
      size="small"
      value={search}
      onChange={(event) => {
        setSearch(event.target.value);
        onChange(event);
      }}
      sx={{ display: show ? "block" : "none" }}
    />
  );
}
