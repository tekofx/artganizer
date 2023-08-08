import {
  Checkbox,
  Rating,
  Button,
  Menu,
  MenuItem,
  Stack,
  Paper,
  Popover,
  Grid,
  Typography,
  Chip,
  TextField,
} from "@mui/material";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import { useState, MouseEvent, useContext } from "react";
import { DataContext } from "../../../pages/_app";
import Tag from "../../../interfaces/Tag";

export default function TagFilter() {
  const { data, setData } = useContext(DataContext);

  const [value, setValue] = useState<number | null>(2);
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const open = Boolean(anchorEl);
  const handleClick = (event: MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleCheckboxChange = (
    event: React.ChangeEvent<HTMLInputElement>,
    tag: Tag
  ) => {
    if (event.target.checked) {
      const newData = { ...data };
      newData.filters.tags = [...data.filters.tags, tag];
      setData(newData);

      console.log("Tag filter: Added tag " + tag.name);
    } else {
      const newData = { ...data };
      newData.filters.tags = newData.filters.tags.filter((t) => t.id != tag.id);
      setData(newData);
      console.log("Tag filter: Removed tag " + tag.name);
    }
    console.log("Tag filter " + data.filters);
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
        Tags
      </Button>
      <Popover
        open={open}
        anchorEl={anchorEl}
        onClose={handleClose}
        anchorOrigin={{
          vertical: "bottom",
          horizontal: "left",
        }}
      >
        <Paper sx={{ p: 2 }}>
          <Grid container spacing={2}>
            <Grid item lg={6}>
              <Typography>Selected Tags</Typography>
              {data.filters.tags.map((tag) => (
                <Chip label={tag.name} />
              ))}
            </Grid>
            <Grid item lg={6}>
              <TextField label="Search tags" size="small" />
              {data.tags.map((tag) => (
                <MenuItem key={tag.id} sx={{ p: 0 }}>
                  <Checkbox
                    value={tag}
                    onChange={(event) => {
                      handleCheckboxChange(event, tag);
                    }}
                  />
                  {tag.name}
                </MenuItem>
              ))}
            </Grid>
          </Grid>
        </Paper>
      </Popover>
    </div>
  );
}
