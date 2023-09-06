import { Button, Menu, Badge } from "@mui/material";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import { useState, MouseEvent, useContext, useEffect } from "react";
import { ChromePicker } from "react-color";
import ColorLensIcon from "@mui/icons-material/ColorLens";
import { DataContext } from "../../../../pages/_app";

export default function RatingFilter() {
  const { data, setData } = useContext(DataContext);
  const [color, setColor] = useState<string>("");
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const [invisible, setInvisible] = useState<boolean>(true);

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

  useEffect(() => {
    if (color != "") {
      setInvisible(false);
      setData((prevData) => ({
        ...prevData,
        filters: { ...prevData.filters, color: color },
      }));
    }
  }, [color]);

  useEffect(() => {
    if (data.filters.color == "") {
      setInvisible(true);
      setColor("");
    }
  }, [data.filters.color]);

  return (
    <div>
      <Button
        id="basic-button"
        aria-controls={open ? "basic-menu" : undefined}
        aria-haspopup="true"
        aria-expanded={open ? "true" : undefined}
        onClick={handleClick}
        endIcon={<ExpandMoreIcon />}
        startIcon={
          <Badge variant="dot" color="error" invisible={invisible}>
            <ColorLensIcon />
          </Badge>
        }
      >
        Color
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
        <Button onClick={handleClose}>Close</Button>
      </Menu>
    </div>
  );
}
