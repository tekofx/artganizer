import InputAdornment from "@mui/material/InputAdornment";
import TextField from "@mui/material/TextField";
import React from "react";

interface Props {
  maxLength: number;
  label: string;
  multiline?: boolean;
  fullWidth?: boolean;
  value: string;
  // eslint-disable-next-line no-unused-vars
  onChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
}

export default function LimitedTextField({ maxLength, label, multiline, fullWidth, value, onChange }: Props) {


  return (
    <TextField
      label={label}
      multiline={multiline ? true : false}
      fullWidth={fullWidth ? true : false}
      value={value}
      onChange={onChange}
      inputProps={{ maxLength: maxLength }}
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
