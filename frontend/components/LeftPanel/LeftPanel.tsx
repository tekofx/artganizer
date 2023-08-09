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

export default function LeftPanel() {
  const [anchorElUser, setAnchorElUser] = useState<null | HTMLElement>(null);
  const router = useRouter();

  const handleOpenUserMenu = (event: MouseEvent<HTMLElement>) => {
    setAnchorElUser(event.currentTarget);
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
