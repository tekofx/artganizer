import { Button, Menu } from "@mui/material";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import { useState, MouseEvent } from "react";
import { ChromePicker } from "react-color";
import ColorLensIcon from "@mui/icons-material/ColorLens";

export default function RatingFilter() {
  const [color, setColor] = useState<string>("");
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const open = Boolean(anchorEl);
  const handleClick = (event: MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleColorChange = (color: any) => {
    setColor(color.hex);
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
        startIcon={<ColorLensIcon />}
      >
        Colors
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
        <ChromePicker color={color} onChange={handleColorChange} />
        <Button onClick={handleClose}>Apply</Button>
      </Menu>
    </div>
  );
}
