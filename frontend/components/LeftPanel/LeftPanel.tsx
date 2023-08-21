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
import { useRouter } from "next/router";
import FolderAccordion from "./FolderAccordion";
import ArtistAccordion from "./ArtistAccordion";
import Snack from "../Snack";
import AlertMessage from "../../interfaces/AlertMessage";
import { ArtistForm, SubmissionForm, TagForm } from "../Forms";
import CreateMenu from "./CreateMenu";
const settings = ["Profile", "Account", "Dashboard", "Logout"];

export default function LeftPanel() {
  const [anchorElUser, setAnchorElUser] = useState<null | HTMLElement>(null);

  const [openArtistForm, setOpenArtistForm] = useState<boolean>(false);
  const [openSubmissionForm, setOpenSubmissionForm] = useState<boolean>(false);
  const [openTagForm, setOpenTagForm] = useState<boolean>(false);
  const [openSnack, setOpenSnack] = useState<boolean>(false);
  const [alertMessage, setAlertMessage] = useState<AlertMessage>({
    message: "Submission created",
    severity: "success",
  });

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
              <CreateMenu
                setOpenArtistForm={setOpenArtistForm}
                setOpenSubmissionForm={setOpenSubmissionForm}
                setOpenTagForm={setOpenTagForm}
              />
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
      <ArtistForm
        open={openArtistForm}
        setOpen={setOpenArtistForm}
        setAlertMessage={setAlertMessage}
        setOpenSnack={setOpenSnack}
      />
      <SubmissionForm
        open={openSubmissionForm}
        setOpen={setOpenSubmissionForm}
        setOpenSnack={setOpenSnack}
        setAlertMessage={setAlertMessage}
      />

      <TagForm
        open={openTagForm}
        setOpen={setOpenTagForm}
        setAlertMessage={setAlertMessage}
        setOpenSnack={setOpenSnack}
      />
      <Snack
        open={openSnack}
        setOpen={setOpenSnack}
        alertMessage={alertMessage}
      />
    </Paper>
  );
}
