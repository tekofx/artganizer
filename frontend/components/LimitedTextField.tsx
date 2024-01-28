import InputAdornment from "@mui/material/InputAdornment";
import TextField from "@mui/material/TextField";
import React, { useState } from "react";

interface Props {
  maxLength: number;
  label: string;
  multiline: boolean;
  value: string;
  // eslint-disable-next-line no-unused-vars
  onChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
}

export default function LimitedTextField(props: Props) {
  const [value, setValue] = useState(props.value);

  const handleChange = (event: {
    target: { value: React.SetStateAction<string> };
  }) => {
    setValue(event.target.value);
  };

  return (
    <TextField
      label={props.label}
      multiline={props.multiline}
      value={value}
      onChange={handleChange}
      inputProps={{ maxLength: props.maxLength }}
      InputProps={{
        endAdornment: (
          <InputAdornment position="end">
            {props.maxLength - value.length}
          </InputAdornment>
        ),
      }}
    />
  );
}
