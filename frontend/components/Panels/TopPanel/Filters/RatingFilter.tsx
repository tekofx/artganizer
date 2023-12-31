import { Rating, Button, Menu, MenuItem, Badge } from "@mui/material";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import { useState, MouseEvent, useContext, useEffect } from "react";
import { DataContext } from "../../../../pages/_app";
import GradeIcon from "@mui/icons-material/Grade";
export default function RatingFilter() {
  const { data, setData } = useContext(DataContext);
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
    if (data.filters.rating == -1) {
      setInvisible(true);
    } else {
      setInvisible(false);
    }
  }, [data.filters.rating]);
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
            value={data.filters.rating}
            onChange={(event, newValue) => {
              setData((prevData) => ({
                ...prevData,
                filters: {
                  ...prevData.filters,
                  rating: newValue || -1,
                },
              }));
            }}
          />
        </MenuItem>
      </Menu>
    </div>
  );
}
