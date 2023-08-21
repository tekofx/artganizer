import {
  IconButton,
  Menu,
  MenuItem,
  Stack,
  Tooltip,
  Typography,
} from "@mui/material";
import { useState, MouseEvent } from "react";
import MenuIcon from "@mui/icons-material/Menu";
import ManageTags from "../Forms/ManageTags";
import SettingsIcon from "@mui/icons-material/Settings";
import LocalOfferIcon from "@mui/icons-material/LocalOffer";
import SettingsDialog from "../SettingsDialog";
export default function SettingMenu() {
  const [anchorElUser, setAnchorElUser] = useState<null | HTMLElement>(null);
  const handleOpenUserMenu = (event: MouseEvent<HTMLElement>) => {
    setAnchorElUser(event.currentTarget);
  };
  const [openTagManager, setOpenTagManager] = useState<boolean>(false);
  const [openSettings, setOpenSettings] = useState<boolean>(true);

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };
  const Settings = [
    {
      name: "Manage Tags",
      icon: <LocalOfferIcon />,
      onclick: () => {
        handleCloseUserMenu();
        setOpenTagManager(true);
      },
    },
    {
      name: "Settings",
      icon: <SettingsIcon />,
      onclick: () => {
        handleCloseUserMenu();
        setOpenSettings(true);
      },
    },
  ];

  return (
    <>
      <Tooltip title="Open settings">
        <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
          <MenuIcon />
        </IconButton>
      </Tooltip>
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
        {Settings.map((setting) => (
          <MenuItem onClick={setting.onclick} key={setting.name}>
            <Stack direction="row" spacing={1}>
              {setting.icon}
              <Typography textAlign="center">{setting.name}</Typography>
            </Stack>
          </MenuItem>
        ))}
      </Menu>
      <ManageTags open={openTagManager} setOpen={setOpenTagManager} />
      <SettingsDialog open={openSettings} setOpen={setOpenSettings} />
    </>
  );
}
