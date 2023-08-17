import React, { useState } from "react";
import TextField from "@mui/material/TextField";
import InputAdornment from "@mui/material/InputAdornment";

export default function LimitedTextField({ maxLength, ...props }) {
  const [value, setValue] = useState("");

  const handleChange = (event) => {
    setValue(event.target.value);
  };

  return (
    <TextField
      {...props}
      value={value}
      onChange={handleChange}
      inputProps={{ maxLength }}
      InputProps={{
        endAdornment: (
          <InputAdornment position="end">
            {maxLength - value.length}
          </InputAdornment>
        ),
      }}
    />
  );
}
