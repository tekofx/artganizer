import { Stack, Grid, MenuItem, Typography, Menu, Tooltip, Avatar, IconButton, Paper, MenuList, Divider, Accordion, AccordionSummary, AccordionDetails, Icon, TextField } from "@mui/material"
import { useState, MouseEvent, useEffect } from "react";
import MenuIcon from '@mui/icons-material/Menu';
import AddIcon from '@mui/icons-material/Add';
import FolderIcon from '@mui/icons-material/Folder';
import LocalOfferIcon from '@mui/icons-material/LocalOffer';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { useRouter } from 'next/router'
import axios from "axios";
import Label from "../interfaces/Label";

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


interface Folder {
    id: number;
    name: string;
    description: string;
}
function FolderAccordion() {
    const [folders, setFolders] = useState<Folder[]>([]);
    const [expanded, setExpanded] = useState<boolean>(true);
    const [newFolder, setNewFolder] = useState<string>("");
    const [showCreateFolder, setShowCreateFolder] = useState<boolean>(false);

    async function createFolder() {
        console.log(newFolder);
        await axios.post("http://localhost:3001/folders", {

            name: newFolder,

        })
            .then((response) => {
                setFolders(response.data);
                setNewFolder("");

            }
            )
            .catch((error) => {
                console.log(error);
            }
            );

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
                expandIcon={<ExpandMoreIcon onClick={() => setExpanded(!expanded)} />}
                aria-controls="panel1a-content"
                id="panel1a-header"
                sx={{ flexDirection: "row-reverse", justifyContent: "space-between" }}
            >
                <Stack direction="row" spacing={1} alignItems="center">

                    <Typography onClick={() => setExpanded(!expanded)}>Folders</Typography>
                    <IconButton onClick={() => setShowCreateFolder(true)}>
                        <AddIcon />
                    </IconButton>
                </Stack>
            </AccordionSummary>
            <AccordionDetails>
                <Stack direction="column">
                    {folders.map((folder) => (
                        <MenuItem key={folder.id}>
                            <Stack direction="row" spacing={1}>
                                <Icon >
                                    <FolderIcon />
                                </Icon>

                                <Typography>{folder.name}</Typography>
                            </Stack>
                        </MenuItem>
                    ))}
                    {showCreateFolder && (
                        <MenuItem>
                            <Stack direction="row">

                                <TextField
                                    id="outlined-basic"
                                    label="Folder name"
                                    variant="outlined"
                                    fullWidth={false}
                                    value={newFolder}
                                    onChange={(e) => setNewFolder(e.target.value)}
                                />
                                <IconButton onClick={createFolder} >
                                    <AddIcon />
                                </IconButton>
                            </Stack>
                        </MenuItem>
                    )}
                </Stack>
            </AccordionDetails>
        </Accordion>
    )
}
function LabelAccordion() {
    const [labels, setLabels] = useState<Label[]>([]);
    const [expanded, setExpanded] = useState<boolean>(true);
    const [newLabel, setNewLabel] = useState<string>("");
    const [showCreateLabel, setShowCreateLabel] = useState<boolean>(false);
    const router = useRouter()


    async function createLabel() {
        await axios.post("http://localhost:3001/labels", {

            name: newLabel,

        })
            .then((response) => {
                setLabels(response.data);
                setNewLabel("");

            }
            )
            .catch((error) => {
                console.log(error);
            }
            );

    }

    const navigateToLabel = (label: Label) => {
        console.log(label);
        router.push(`/label/${label.id}`);
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
                expandIcon={<ExpandMoreIcon onClick={() => setExpanded(!expanded)} />}
                aria-controls="panel1a-content"
                id="panel1a-header"
                sx={{ flexDirection: "row-reverse", justifyContent: "space-between" }}
            >
                <Stack direction="row" spacing={1} alignItems="center">

                    <Typography onClick={() => setExpanded(!expanded)}>Labels</Typography>
                    <IconButton onClick={() => setShowCreateLabel(true)}>
                        <AddIcon />
                    </IconButton>
                </Stack>
            </AccordionSummary>
            <AccordionDetails>
                <Stack direction="column">
                    {labels.map((label) => (
                        <MenuItem key={label.id} onClick={() => navigateToLabel(label)}>
                            <Stack direction="row" spacing={1}>
                                <Icon sx={{ color: label.color }}>
                                    <LocalOfferIcon />
                                </Icon>

                                <Typography>{label.name}</Typography>
                            </Stack>
                        </MenuItem>
                    ))}
                    {showCreateLabel && (
                        <MenuItem >
                            <Stack direction="row">

                                <TextField
                                    id="outlined-basic"
                                    label="Label name"
                                    variant="outlined"
                                    fullWidth={false}
                                    value={newLabel}
                                    onChange={(e) => setNewLabel(e.target.value)}
                                />
                                <IconButton onClick={createLabel} >
                                    <AddIcon />
                                </IconButton>
                            </Stack>
                        </MenuItem>
                    )}
                </Stack>
            </AccordionDetails>
        </Accordion>
    )
}

export default function LateralPanel() {
    const [anchorElNav, setAnchorElNav] = useState<null | HTMLElement>(null);
    const [anchorElUser, setAnchorElUser] = useState<null | HTMLElement>(null);
    const router = useRouter()

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
                    <Grid container justifyContent="space-between" alignItems="center">
                        <Grid item>

                            <Tooltip title="Open settings">
                                <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                                    <MenuIcon />
                                </IconButton>
                            </Tooltip>
                        </Grid>
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
                        <Grid item>

                            <Typography variant="h5" onClick={() => router.push("/")} sx={{ cursor: "pointer" }}>Artganizer</Typography>
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