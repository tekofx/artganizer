import { TextField } from "@mui/material";
import { useEffect, useRef } from "react";

interface Props {
  onChange: (
    // eslint-disable-next-line no-unused-vars
    event: React.ChangeEvent<HTMLInputElement>
  ) => void;
  value?: string;
  focus?: boolean;
  show?: boolean;
  fullWidth?: boolean;
}
export default function SearchBar({
  onChange,
  show,
  value,
  focus,
  fullWidth,
}: Props) {
  const inputRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    if (show && focus) {
      inputRef.current?.focus();
    }
  }, [show, focus]);
  return (
    <TextField
      inputRef={inputRef}
      id="outlined-basic"
      label="Search"
      variant="outlined"
      size="small"
      value={value}
      onChange={onChange}
      fullWidth={fullWidth}
      sx={{ display: show ? "block" : "none" }}
    />
  );
}
