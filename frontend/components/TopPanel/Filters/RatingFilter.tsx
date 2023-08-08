import {
  Checkbox,
  Rating,
  Button,
  Menu,
  MenuItem,
  Stack,
  Paper,
} from "@mui/material";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import { useState, MouseEvent, useContext } from "react";
import { DataContext } from "../../../pages/_app";
import Tag from "../../../interfaces/Tag";
export default function RatingFilter() {
  const { data, setData } = useContext(DataContext);
  const [value, setValue] = useState<number | null>(data.filters.rating);
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const open = Boolean(anchorEl);
  const handleClick = (event: MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  return (
    <div>
      <Button
        id="basic-button"
        aria-controls={open ? "basic-menu" : undefined}
        aria-haspopup="true"
        aria-expanded={open ? "true" : undefined}
        onClick={handleClick}
        endIcon={<ExpandMoreIcon />}
      >
        Rating
      </Button>
      <Menu
        id="basic-menu"
        anchorEl={anchorEl}
        open={open}
        onClose={handleClose}
        MenuListProps={{
          "aria-labelledby": "basic-button",
        }}
      >
        <MenuItem onClick={handleClose}>
          <Rating
            name="simple-controlled"
            value={data.filters.rating}
            onChange={(event, newValue) => {
              const newData = { ...data };
              newData.filters.rating = newValue || -1;
              console.log(`Rating filter: ${newValue}`);
              setData(newData);
            }}
          />
        </MenuItem>
      </Menu>
    </div>
  );
}
