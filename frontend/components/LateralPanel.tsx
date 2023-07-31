import {
    Stack, Grid, MenuItem, Typography, Menu, Tooltip, Avatar, IconButton, Paper, MenuList,
    Divider, Accordion, AccordionSummary, AccordionDetails, Icon, TextField, Dialog, Button
} from "@mui/material"
import { useState, MouseEvent, useEffect, useContext } from "react";
import MenuIcon from '@mui/icons-material/Menu';
import AddIcon from '@mui/icons-material/Add';
import FolderIcon from '@mui/icons-material/Folder';
import LocalOfferIcon from '@mui/icons-material/LocalOffer';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import CheckIcon from '@mui/icons-material/Check';
import ClearIcon from '@mui/icons-material/Clear';
import PaletteIcon from '@mui/icons-material/Palette';
import { useRouter } from 'next/router'
import axios from "axios";
import { DataContext } from "../pages/_app";

import { TwitterPicker } from 'react-color';
import Folder from "../interfaces/Folder";
import { Tag } from "@mui/icons-material";
const settings = ['Profile', 'Account', 'Dashboard', 'Logout'];


function FolderList() {
    const { data, setData } = useContext(DataContext);
    return (
        <>
            {data.folders.length == 0 && (

                <Typography>No folders</Typography>
            )
            }
            {
                data.folders.map((folder) => (
                    <MenuItem key={folder.id} >
                        <Stack direction="row" spacing={1}>
                            <Icon >
                                <FolderIcon />
                            </Icon>

                            <Typography>{folder.name}</Typography>
                        </Stack>
                    </MenuItem>
                ))
            }
        </>
    )
}

function FolderAccordion() {
    const [expanded, setExpanded] = useState<boolean>(true);
    const [newFolder, setNewFolder] = useState<string>("Folder");
    const [showCreateFolder, setShowCreateFolder] = useState<boolean>(false);
    const [textFieldError, setTextFieldError] = useState<boolean>(false);
    const router = useRouter()


    const navigateToFolder = (folder: Folder) => {
        console.log(folder);
        router.push(`/folder/${folder.id}`);
    }

    const onTextFieldChange = (event: any) => {
        setNewFolder(event.target.value);
        if (event.target.value == "") {
            setTextFieldError(true);
        } else {
            setTextFieldError(false);
        }
    };

    async function createFolder() {
        await axios.post("http://localhost:3001/folders", {
            name: newFolder,
        }).then((response) => {
            data.folders = response.data;
            setData(data);
            setNewFolder("");

        }).catch((error) => {
            console.log(error);
        });
        setShowCreateFolder(false);
    }

    const { data, setData } = useContext(DataContext);

    return (
        <Accordion expanded={expanded}>
            <AccordionSummary
                expandIcon={<ExpandMoreIcon onClick={() => setExpanded(!expanded)} />}
                aria-controls="panel1a-content"
                id="panel1a-header"
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
                    <FolderList />
                    {showCreateFolder && (
                        <>
                            <Stack direction="row">

                                <TextField
                                    id="outlined-basic"
                                    label="Folder name"
                                    variant="outlined"
                                    fullWidth={false}
                                    error={textFieldError}
                                    helperText={textFieldError ? "Folder name cannot be empty" : ""}
                                    value={newFolder}
                                    onChange={onTextFieldChange}
                                />
                                <IconButton onClick={createFolder} >
                                    <AddIcon />
                                </IconButton>
                            </Stack>
                        </>
                    )}
                </Stack>
            </AccordionDetails>
        </Accordion >
    )
}

function TagList() {
    const { data, setData } = useContext(DataContext);
    return (
        <>
            {data.tags.length == 0 && (

                <Typography>No tags</Typography>
            )
            }
            {
                data.tags.map((tag) => (
                    <MenuItem key={tag.id} >
                        <Stack direction="row" spacing={1}>
                            <Icon sx={{ color: tag.color }}>
                                <LocalOfferIcon />
                            </Icon>

                            <Typography>{tag.name}</Typography>
                        </Stack>
                    </MenuItem>
                ))
            }
        </>
    )
}

function TagAccordion() {
    const [expanded, setExpanded] = useState<boolean>(true);
    const [newTag, setNewTag] = useState<string>("Tag");
    const [showCreateTag, setShowCreateTag] = useState<boolean>(false);
    const [colorSelect, setColorSelect] = useState(false);
    const [color, setColor] = useState<string>("#ffffff");
    const [textFieldError, setTextFieldError] = useState<boolean>(false);
    const { data, setData } = useContext(DataContext);

    const toggleColorSelect = () => {
        setColorSelect(!colorSelect);
    };

    const handleColorChange = (color: any, event: any) => {
        setColor(color.hex);
    };

    const onTextFieldChange = (event: any) => {
        setNewTag(event.target.value);
        if (event.target.value == "") {
            setTextFieldError(true);
        } else {
            setTextFieldError(false);
        }
    };


    async function createTag() {
        if (newTag == "") {
            return;
        }

        await axios.post("http://localhost:3001/tags", {
            name: newTag,
            color: color
        }).then((response) => {
            data.tags = response.data;
            setData(data);
            setNewTag("");

        }).catch((error) => {
            console.log(error);
        });

        setShowCreateTag(false);

    }




    return (
        <Accordion expanded={expanded}>
            <AccordionSummary
                expandIcon={<ExpandMoreIcon onClick={() => setExpanded(!expanded)} />}
                aria-controls="panel1a-content"
                id="panel1a-header"
                sx={{ flexDirection: "row-reverse", justifyContent: "space-between" }}
            >
                <Stack direction="row" spacing={1} alignItems="center">

                    <Typography onClick={() => setExpanded(!expanded)}>Tags</Typography>
                    <IconButton onClick={() => setShowCreateTag(true)}>
                        <AddIcon />
                    </IconButton>
                </Stack>
            </AccordionSummary>
            <AccordionDetails>
                <Stack direction="column">
                    <TagList />
                    {showCreateTag && (
                        <>
                            <Stack direction="row" sx={{ paddingBottom: "2%" }}>
                                <IconButton onClick={toggleColorSelect}  >
                                    <PaletteIcon sx={{ color: color }} />
                                </IconButton>

                                <TextField
                                    id="outlined-basic"
                                    label="Label name"
                                    variant="outlined"
                                    error={textFieldError}
                                    helperText={textFieldError ? "Tag name cannot be empty" : ""}
                                    fullWidth={false}
                                    value={newTag}
                                    onChange={onTextFieldChange}
                                />
                                <IconButton onClick={createTag} >
                                    <CheckIcon />
                                </IconButton>
                                <IconButton onClick={() => setShowCreateTag(false)} >
                                    <ClearIcon />
                                </IconButton>
                            </Stack>
                            <TwitterPicker onChange={handleColorChange} color={color} />
                        </>
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
                        <TagAccordion />
                        <FolderAccordion />

                    </MenuList>

                </Grid>
            </Grid>
        </Paper >
    )
}