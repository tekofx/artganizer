import MenuIcon from "@mui/icons-material/Menu";
import {
  IconButton,
  Tooltip
} from "@mui/material";
import { MouseEvent, useState } from "react";
import MoreMenu from "./Menu";
export default function SettingMenu() {
  const [anchorElUser, setAnchorElUser] = useState<null | HTMLElement>(null);
  const handleOpenUserMenu = (event: MouseEvent<HTMLElement>) => {
    setAnchorElUser(event.currentTarget);
  };


  return (
    <>
      <Tooltip title="Open settings">
        <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
          <MenuIcon />
        </IconButton>
      </Tooltip>
      <MoreMenu anchorElUser={anchorElUser} setAnchorElUser={setAnchorElUser} />
    </>
  );
}
