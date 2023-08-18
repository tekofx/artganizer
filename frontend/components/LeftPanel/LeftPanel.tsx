import {
  Grid,
  MenuItem,
  Typography,
  Menu,
  Tooltip,
  IconButton,
  Paper,
  MenuList,
  Dialog,
} from "@mui/material";
import { useState, MouseEvent } from "react";
import MenuIcon from "@mui/icons-material/Menu";
import AddIcon from "@mui/icons-material/Add";
import { useRouter } from "next/router";
import FolderAccordion from "./FolderAccordion";
import ArtistAccordion from "./ArtistAccordion";
import ArtistForm from "../Forms/ArtistForm";
import SubmissionForm from "../Forms/SubmissionForm";
import TagForm from "../Forms/TagForm";
const settings = ["Profile", "Account", "Dashboard", "Logout"];

export default function LeftPanel() {
  const [anchorElUser, setAnchorElUser] = useState<null | HTMLElement>(null);
  const [anchorCreateMenu, setAnchorCreateMenu] = useState<null | HTMLElement>(
    null
  );

  const [openSubmissionForm, setOpenSubmissionForm] = useState<boolean>(false);
  const [openArtistForm, setOpenArtistForm] = useState<boolean>(false);
  const [openTagForm, setOpenTagForm] = useState<boolean>(false);

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

  const handleCloseCreateMenu = () => {
    setAnchorCreateMenu(null);
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
              <MenuItem onClick={() => setOpenArtistForm(true)}>
                <Typography textAlign="center">Artist</Typography>
              </MenuItem>
              <MenuItem onClick={() => setOpenSubmissionForm(true)}>
                <Typography textAlign="center">Submission</Typography>
              </MenuItem>
              <MenuItem onClick={() => setOpenTagForm(true)}>
                <Typography textAlign="center">Tag</Typography>
              </MenuItem>
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
      <ArtistForm open={openArtistForm} setOpen={setOpenArtistForm} />
      <SubmissionForm
        open={openSubmissionForm}
        setOpen={setOpenSubmissionForm}
      />
      <TagForm open={openTagForm} setOpen={setOpenTagForm} />
    </Paper>
  );
}
