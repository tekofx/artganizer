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

function ArtistList() {
  const { data, setData } = useContext(DataContext);
  const router = useRouter();

  return (
    <>
      {data.artists.length == 0 && <Typography>No artists</Typography>}
      {data.artists.map((artist) => (
        <MenuItem
          key={artist.id}
          onClick={() => router.push(`/artist/${artist.id}`)}
        >
          <Stack direction="row" spacing={1}>
            <Typography>{artist.name}</Typography>
          </Stack>
        </MenuItem>
      ))}
    </>
  );
}

export default function ArtistAccordion() {
  const [expanded, setExpanded] = useState<boolean>(true);

  const [anchorEl, setAnchorEl] = useState<HTMLButtonElement | null>(null);

  const toggleColorSelect = (event: MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const open = Boolean(anchorEl);
  const id = open ? "simple-popover" : undefined;

  return (
    <Accordion expanded={expanded}>
      <AccordionSummary
        expandIcon={<ExpandMoreIcon onClick={() => setExpanded(!expanded)} />}
        aria-controls="panel1a-content"
        id="panel1a-header"
        sx={{ flexDirection: "row-reverse", justifyContent: "space-between" }}
      >
        <Typography onClick={() => setExpanded(!expanded)}>Artists</Typography>
      </AccordionSummary>
      <AccordionDetails>
        <Stack direction="column">
          <ArtistList />
        </Stack>
      </AccordionDetails>
    </Accordion>
  );
}
