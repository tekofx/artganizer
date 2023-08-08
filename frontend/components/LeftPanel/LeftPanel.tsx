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
import MenuIcon from "@mui/icons-material/Menu";
import AddIcon from "@mui/icons-material/Add";
import FolderIcon from "@mui/icons-material/Folder";
import LocalOfferIcon from "@mui/icons-material/LocalOffer";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import CheckIcon from "@mui/icons-material/Check";
import ClearIcon from "@mui/icons-material/Clear";
import PaletteIcon from "@mui/icons-material/Palette";
import { useRouter } from "next/router";
import axios from "axios";
import { DataContext } from "../../pages/_app";
import FolderAccordion from "./FolderAccordion";
import ArtistAccordion from "./ArtistAccordion";

import { TwitterPicker } from "react-color";
import Folder from "../../interfaces/Folder";
import { Tag } from "@mui/icons-material";
const settings = ["Profile", "Account", "Dashboard", "Logout"];

export default function LeftPanel() {
  const [anchorElNav, setAnchorElNav] = useState<null | HTMLElement>(null);
  const [anchorElUser, setAnchorElUser] = useState<null | HTMLElement>(null);
  const router = useRouter();

  const handleOpenNavMenu = (event: MouseEvent<HTMLElement>) => {
    setAnchorElNav(event.currentTarget);
  };
  const handleOpenUserMenu = (event: MouseEvent<HTMLElement>) => {
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseNavMenu = () => {
    setAnchorElNav(null);
  };

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };

  return (
    <Paper>
      <Grid container sx={{ p: 2 }}>
        <Grid item lg={12}>
          <Grid container justifyContent="space-between" alignItems="center">
            <Grid item>
              <Tooltip title="Open settings">
                <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                  <MenuIcon />
                </IconButton>
              </Tooltip>
            </Grid>
            <Menu
              sx={{ mt: "45px" }}
              id="menu-appbar"
              anchorEl={anchorElUser}
              anchorOrigin={{
                vertical: "top",
                horizontal: "right",
              }}
              keepMounted
              transformOrigin={{
                vertical: "top",
                horizontal: "right",
              }}
              open={Boolean(anchorElUser)}
              onClose={handleCloseUserMenu}
              color="white"
            >
              {settings.map((setting) => (
                <MenuItem key={setting} onClick={handleCloseUserMenu}>
                  <Typography textAlign="center">{setting}</Typography>
                </MenuItem>
              ))}
            </Menu>
            <Grid item>
              <Typography
                variant="h5"
                onClick={() => router.push("/")}
                sx={{ cursor: "pointer" }}
              >
                Artganizer
              </Typography>
            </Grid>
            <Grid item>
              <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                <AddIcon />
              </IconButton>
            </Grid>
          </Grid>
        </Grid>
        <Grid item lg={12}>
          <MenuList>
            <FolderAccordion />
            <ArtistAccordion />
          </MenuList>
        </Grid>
      </Grid>
    </Paper>
  );
}
