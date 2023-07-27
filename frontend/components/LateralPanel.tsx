import { Stack, Grid, MenuItem, Typography, Menu, Tooltip, Avatar, IconButton, Paper, MenuList, Divider, Accordion, AccordionSummary, AccordionDetails, Icon } from "@mui/material"
import { useState, MouseEvent, useEffect } from "react";
import MenuIcon from '@mui/icons-material/Menu';
import AddIcon from '@mui/icons-material/Add';
import FolderIcon from '@mui/icons-material/Folder';
import LocalOfferIcon from '@mui/icons-material/LocalOffer';
import axios from "axios";


const settings = ['Profile', 'Account', 'Dashboard', 'Logout'];

const defaultPages = [
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

interface Page {
    name: string;
    icon: JSX.Element;
}

interface Label {
    name: string;
    color: string;
}

interface Folder {
    name: string;
    description: string;
}
function FolderAccordion() {
    const [folders, setFolders] = useState<Folder[]>([]);
    const [expanded, setExpanded] = useState<boolean>(true);

    const onClick = () => {
        setExpanded(!expanded);
    }

    useEffect(() => {
        axios.get("http://localhost:3001/folders", {
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json',
            }
        })
            .then((response) => {
                setFolders(response.data);
            })
            .catch((error) => {
                console.log(error);
            });
    }, []);

    return (
        <Accordion expanded={expanded}>
            <AccordionSummary
                expandIcon={<AddIcon />}
                aria-controls="panel1a-content"
                id="panel1a-header"
                onClick={onClick}
            >
                <Typography>Folders</Typography>
            </AccordionSummary>
            <AccordionDetails>
                <Stack direction="column">
                    {folders.map((folder) => (
                        <MenuItem>
                            <Stack direction="row" spacing={1}>
                                <Icon >
                                    <FolderIcon />
                                </Icon>

                                <Typography>{folder.name}</Typography>
                            </Stack>
                        </MenuItem>
                    ))}
                </Stack>
            </AccordionDetails>
        </Accordion>
    )
}
function LabelAccordion() {
    const [labels, setLabels] = useState<Label[]>([]);
    const [expanded, setExpanded] = useState<boolean>(true);

    const onClick = () => {
        setExpanded(!expanded);
    }

    useEffect(() => {
        axios.get("http://localhost:3001/labels", {
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json',
            }
        })
            .then((response) => {
                setLabels(response.data);
            })
            .catch((error) => {
                console.log(error);
            });
    }, []);

    return (
        <Accordion expanded={expanded}>
            <AccordionSummary
                expandIcon={<AddIcon />}
                aria-controls="panel1a-content"
                id="panel1a-header"
                onClick={onClick}
            >
                <Typography>Labels</Typography>
            </AccordionSummary>
            <AccordionDetails>
                <Stack direction="column">
                    {labels.map((label) => (
                        <MenuItem>
                            <Stack direction="row" spacing={1}>
                                <Icon sx={{ color: label.color }}>
                                    <LocalOfferIcon />
                                </Icon>

                                <Typography>{label.name}</Typography>
                            </Stack>
                        </MenuItem>
                    ))}
                </Stack>
            </AccordionDetails>
        </Accordion>
    )
}

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
                        <LabelAccordion />
                        <FolderAccordion />
                        <Divider />
                        <MenuItem>
                            <Stack direction="row" >
                                <Typography>Carpetas</Typography>
                                <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                                    <AddIcon />
                                </IconButton>
                            </Stack>
                        </MenuItem>
                    </MenuList>

                </Grid>
            </Grid>
        </Paper >
    )
}