import {
  Grid,
  MenuItem,
  Typography,
  Menu,
  Tooltip,
  IconButton,
  Paper,
  MenuList,
} from "@mui/material";
import { useState, MouseEvent } from "react";
import MenuIcon from "@mui/icons-material/Menu";
import AddIcon from "@mui/icons-material/Add";
import { useRouter } from "next/router";
import FolderAccordion from "./FolderAccordion";
import ArtistAccordion from "./ArtistAccordion";

const settings = ["Profile", "Account", "Dashboard", "Logout"];

interface createOption {
  name: string;
  navigate: string;
}
const createOptions = [
  { name: "Submission", navigate: "/createSubmission" },
  { name: "Artist", navigate: "/createArtist" },
];

export default function LeftPanel() {
  const [anchorElUser, setAnchorElUser] = useState<null | HTMLElement>(null);
  const [anchorCreateMenu, setAnchorCreateMenu] = useState<null | HTMLElement>(
    null
  );

  const router = useRouter();

  const handleOpenUserMenu = (event: MouseEvent<HTMLElement>) => {
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };

  const handleOpenCreateMenu = (event: MouseEvent<HTMLElement>) => {
    setAnchorCreateMenu(event.currentTarget);
  };

  const handleCloseCreateMenu = (option: createOption) => {
    setAnchorCreateMenu(null);
    router.push(option.navigate);
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
              <Tooltip title="Create">
                <IconButton onClick={handleOpenCreateMenu} sx={{ p: 0 }}>
                  <AddIcon />
                </IconButton>
              </Tooltip>
            </Grid>
            <Menu
              sx={{ mt: "45px" }}
              id="menu-appbar"
              anchorEl={anchorCreateMenu}
              anchorOrigin={{
                vertical: "top",
                horizontal: "left",
              }}
              keepMounted
              transformOrigin={{
                vertical: "top",
                horizontal: "left",
              }}
              open={Boolean(anchorCreateMenu)}
              onClose={handleCloseCreateMenu}
              color="white"
            >
              {createOptions.map((option: createOption) => (
                <MenuItem
                  key={option.name}
                  onClick={()=>handleCloseCreateMenu(option)}
                >
                  <Typography textAlign="center">{option.name}</Typography>
                </MenuItem>
              ))}
            </Menu>
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
