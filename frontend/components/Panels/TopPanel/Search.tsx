import { TextField, IconButton } from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";
import { useContext } from "react";
import { DataContext } from "../../../pages/_app";
export default function Search() {
  const { data, setData } = useContext(DataContext);
  function onChange(event: React.ChangeEvent<HTMLInputElement>) {
    var newData = { ...data };
    newData.filters.title = event.target.value;
    setData(newData);
  }

  return (
    <div>
      <IconButton>
        <SearchIcon />
      </IconButton>
      <TextField
        id="outlined-basic"
        label="Search"
        variant="outlined"
        size="small"
        value={data.filters.title}
        onChange={onChange}
      />
    </div>
  );
}
