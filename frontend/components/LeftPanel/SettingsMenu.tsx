import { IconButton, Menu, MenuItem, Tooltip, Typography } from "@mui/material";
import { useState, MouseEvent } from "react";
import MenuIcon from "@mui/icons-material/Menu";
import ManageTags from "../Forms/ManageTags";
const settings = ["Profile", "Account", "Dashboard", "Logout"];

export default function SettingMenu() {
  const [anchorElUser, setAnchorElUser] = useState<null | HTMLElement>(null);
  const handleOpenUserMenu = (event: MouseEvent<HTMLElement>) => {
    setAnchorElUser(event.currentTarget);
  };
  const [openTagManager, setOpenTagManager] = useState<boolean>(false);

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };
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
        <MenuItem
          onClick={() => {
            handleCloseUserMenu();
            setOpenTagManager(true);
          }}
        >
          <Typography textAlign="center">Manage Tags</Typography>
        </MenuItem>
      </Menu>
      <ManageTags open={openTagManager} setOpen={setOpenTagManager} />
    </>
  );
}
