import {
  Stack,
  Grid,
  MenuItem,
  Typography,
  Menu,
  Tooltip,
  Avatar,
  IconButton,
  Paper,
  MenuList,
  Divider,
  Accordion,
  AccordionSummary,
  AccordionDetails,
  Icon,
  TextField,
  Dialog,
  Button,
  Popover,
} from "@mui/material";
import { useState, MouseEvent, useEffect, useContext } from "react";
import AddIcon from "@mui/icons-material/Add";
import LocalOfferIcon from "@mui/icons-material/LocalOffer";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import CheckIcon from "@mui/icons-material/Check";
import ClearIcon from "@mui/icons-material/Clear";
import PaletteIcon from "@mui/icons-material/Palette";
import { useRouter } from "next/router";
import axios from "axios";
import { DataContext } from "../../pages/_app";
import { TwitterPicker } from "react-color";

function TagList() {
  const { data, setData } = useContext(DataContext);
  const router = useRouter();

  return (
    <>
      {data.tags.length == 0 && <Typography>No tags</Typography>}
      {data.tags.map((tag) => (
        <MenuItem key={tag.id} onClick={() => router.push(`/tag/${tag.id}`)}>
          <Stack direction="row" spacing={1}>
            <Icon sx={{ color: tag.color }}>
              <LocalOfferIcon />
            </Icon>

            <Typography>{tag.name}</Typography>
          </Stack>
        </MenuItem>
      ))}
    </>
  );
}

export default function TagAccordion() {
  const [expanded, setExpanded] = useState<boolean>(true);
  const [newTag, setNewTag] = useState<string>("Tag");
  const [showCreateTag, setShowCreateTag] = useState<boolean>(false);
  const [colorSelect, setColorSelect] = useState(false);
  const [color, setColor] = useState<string>("#ffffff");
  const [textFieldError, setTextFieldError] = useState<boolean>(false);
  const { data, setData } = useContext(DataContext);

  const [anchorEl, setAnchorEl] = useState<HTMLButtonElement | null>(null);

  const toggleColorSelect = (event: MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const open = Boolean(anchorEl);
  const id = open ? "simple-popover" : undefined;

  const handleColorChange = (color: any, event: any) => {
    setColor(color.hex);
  };

  const onTextFieldChange = (event: any) => {
    setNewTag(event.target.value);
    if (event.target.value == "") {
      setTextFieldError(true);
    } else {
      setTextFieldError(false);
    }
  };

  async function createTag() {
    if (newTag == "") {
      return;
    }

    await axios
      .post("http://localhost:3001/tags", {
        name: newTag,
        color: color,
      })
      .then((response) => {
        data.tags = response.data;
        setData(data);
        setNewTag("");
      })
      .catch((error) => {
        console.log(error);
      });

    setShowCreateTag(false);
  }

  return (
    <Accordion expanded={expanded}>
      <AccordionSummary
        expandIcon={<ExpandMoreIcon onClick={() => setExpanded(!expanded)} />}
        aria-controls="panel1a-content"
        id="panel1a-header"
        sx={{ flexDirection: "row-reverse", justifyContent: "space-between" }}
      >
        <Stack direction="row" spacing={1} alignItems="center">
          <Typography onClick={() => setExpanded(!expanded)}>Tags</Typography>
          <IconButton onClick={() => setShowCreateTag(true)}>
            <AddIcon />
          </IconButton>
        </Stack>
      </AccordionSummary>
      <AccordionDetails>
        <Stack direction="column">
          <TagList />
          {showCreateTag && (
            <>
              <Stack direction="row" sx={{ paddingBottom: "2%" }}>
                <IconButton onClick={toggleColorSelect}>
                  <PaletteIcon sx={{ color: color }} />
                </IconButton>

                <TextField
                  id="outlined-basic"
                  label="Label name"
                  variant="outlined"
                  error={textFieldError}
                  helperText={textFieldError ? "Tag name cannot be empty" : ""}
                  fullWidth={false}
                  value={newTag}
                  onChange={onTextFieldChange}
                />
              </Stack>
              <Popover
                id={id}
                open={open}
                anchorEl={anchorEl}
                onClose={handleClose}
                anchorOrigin={{
                  vertical: "bottom",
                  horizontal: "left",
                }}
              >
                <TwitterPicker onChange={handleColorChange} color={color} />
              </Popover>
              <Button onClick={createTag} startIcon={<CheckIcon />}>
                Aceptar
              </Button>
              <Button
                onClick={() => setShowCreateTag(false)}
                startIcon={<ClearIcon />}
              >
                Cancelar
              </Button>
            </>
          )}
        </Stack>
      </AccordionDetails>
    </Accordion>
  );
}
