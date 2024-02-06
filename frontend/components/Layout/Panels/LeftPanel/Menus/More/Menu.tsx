import BackupIcon from '@mui/icons-material/Backup';
import InfoIcon from "@mui/icons-material/Info";
import LocalOfferIcon from "@mui/icons-material/LocalOffer";
import LogoutIcon from '@mui/icons-material/Logout';
import SettingsIcon from "@mui/icons-material/Settings";
import {
    Menu,
    MenuItem,
    Stack,
    Typography
} from "@mui/material";
import About from "components/Layout/Panels/LeftPanel/Menus/More/About";
import LogOut from 'components/Layout/Panels/LeftPanel/Menus/More/LogOut';
import { Dispatch, SetStateAction, useState } from "react";
import ManageTags from "../../../../../Forms/ManageTags";
import BackupAndRestoreDialog from "./BackupAndRestore";
import SettingsDialog from "./Settings";

interface SettingMenuProps {
    anchorElUser: HTMLElement | null;
    setAnchorElUser: Dispatch<SetStateAction<HTMLElement | null>>

}

export default function SettingMenu({ anchorElUser, setAnchorElUser }: SettingMenuProps) {
    const [openTagManager, setOpenTagManager] = useState<boolean>(false);
    const [openSettings, setOpenSettings] = useState<boolean>(false);
    const [openAbout, setOpenAbout] = useState<boolean>(false);
    const [openBackupAndRestore, setOpenBackupAndRestore] = useState<boolean>(false);
    const [openLogOut, setOpenLogOut] = useState<boolean>(false);

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
        {
            name: "Backup and Restore",
            icon: <BackupIcon />,
            onclick: () => {
                handleCloseUserMenu();
                setOpenBackupAndRestore(true);
            }

        },
        {
            name: "About",
            icon: <InfoIcon />,
            onclick: () => {
                handleCloseUserMenu();
                setOpenAbout(true);
            },
        },
        {
            name: "Log Out",
            icon: <LogoutIcon />,
            onclick: () => {
                handleCloseUserMenu();
                setOpenLogOut(true);
            },
        }
    ];

    return (
        <>

            <Menu
                sx={{
                    top: { xs: -40, lg: 40 },
                }}
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
            <About open={openAbout} setOpen={setOpenAbout} />
            <BackupAndRestoreDialog open={openBackupAndRestore} setOpen={setOpenBackupAndRestore} />
            <LogOut open={openLogOut} setOpen={setOpenLogOut} />
        </>
    );
}
