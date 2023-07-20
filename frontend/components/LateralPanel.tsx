import { Stack, Grid, MenuItem, Typography, Menu, Tooltip, Avatar, IconButton, Paper, MenuList } from "@mui/material"
import { useState, MouseEvent } from "react";
import MenuIcon from '@mui/icons-material/Menu';
import AddIcon from '@mui/icons-material/Add';
import FolderIcon from '@mui/icons-material/Folder';
import LocalOfferIcon from '@mui/icons-material/LocalOffer';
const settings = ['Profile', 'Account', 'Dashboard', 'Logout'];

const pages = [
    {
        name: 'Todo',
        icon: <FolderIcon />

    },
    {
        name: 'Etiquetas',
        icon: <LocalOfferIcon />

    },
    {
        name: 'Todo',
        icon: <AddIcon />

    },
]

export default function LateralPanel() {
    const [anchorElNav, setAnchorElNav] = useState<null | HTMLElement>(null);
    const [anchorElUser, setAnchorElUser] = useState<null | HTMLElement>(null);

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
                    <Tooltip title="Open settings">
                        <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                            <MenuIcon />
                        </IconButton>
                    </Tooltip>
                    <Menu
                        sx={{ mt: '45px' }}
                        id="menu-appbar"
                        anchorEl={anchorElUser}
                        anchorOrigin={{
                            vertical: 'top',
                            horizontal: 'right',
                        }}
                        keepMounted
                        transformOrigin={{
                            vertical: 'top',
                            horizontal: 'right',
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
                    <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                        <AddIcon />
                    </IconButton>

                </Grid>
                <Grid item lg={12}>
                    <MenuList>
                        {pages.map((page) => (
                            <MenuItem key={page.name} onClick={handleCloseNavMenu}>
                                {page.icon}
                                <Typography textAlign="center">{page.name}</Typography>
                            </MenuItem>
                        ))}
                    </MenuList>

                </Grid>
            </Grid>
        </Paper >
    )
}