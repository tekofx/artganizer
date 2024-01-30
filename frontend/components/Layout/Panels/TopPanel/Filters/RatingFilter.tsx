import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import GradeIcon from "@mui/icons-material/Grade";
import { Badge, Button, Menu, MenuItem, Rating } from "@mui/material";
import { MouseEvent, useEffect, useState } from "react";
import { useAppContext } from "../../../../../pages/_app";
export default function RatingFilter() {
  const { filters, setFilters } = useAppContext();
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const [invisible, setInvisible] = useState<boolean>(true);

  const open = Boolean(anchorEl);
  const handleClick = (event: MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };
  useEffect(() => {
    if (filters.rating == -1) {
      setInvisible(true);
    } else {
      setInvisible(false);
    }
  }, [filters.rating]);
  return (
    <div>
      <Button
        id="basic-button"
        aria-controls={open ? "basic-menu" : undefined}
        aria-haspopup="true"
        aria-expanded={open ? "true" : undefined}
        onClick={handleClick}
        startIcon={
          <Badge variant="dot" color="error" invisible={invisible}>
            <GradeIcon />
          </Badge>
        }
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
            value={filters.rating}
            onChange={(event, newValue) => {
              setFilters({ ...filters, rating: newValue || -1 });
            }}
          />
        </MenuItem>
      </Menu>
    </div>
  );
}
