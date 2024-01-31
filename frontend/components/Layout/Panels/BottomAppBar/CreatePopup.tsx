import AddIcon from "@mui/icons-material/Add";
import BrushIcon from "@mui/icons-material/Brush";
import LocalOfferIcon from "@mui/icons-material/LocalOffer";
import PersonIcon from "@mui/icons-material/Person";

import PhotoIcon from "@mui/icons-material/Photo";
import {
    IconButton,
    Menu,
    MenuItem,
    Stack,
    Tooltip,
    Typography,
} from "@mui/material";
import { MouseEvent, useState } from "react";
import { ArtistForm, SubmissionForm, TagForm } from "../../../Forms";
import CharacterForm from "../../../Forms/CharacterForm";

export default function CreateMenu() {
    const [openSubmissionForm, setOpenSubmissionForm] = useState<boolean>(false);
    const [openTagForm, setOpenTagForm] = useState<boolean>(false);
    const [openArtistForm, setOpenArtistForm] = useState<boolean>(false);
    const [openCharacterForm, setOpenCharacterForm] = useState<boolean>(false);
    const [anchorCreateMenu, setAnchorCreateMenu] = useState<null | HTMLElement>(
        null
    );
    const handleCloseCreateMenu = () => {
        setAnchorCreateMenu(null);
    };
    const handleOpenCreateMenu = (event: MouseEvent<HTMLElement>) => {
        setAnchorCreateMenu(event.currentTarget);
    };


    const MenuItems = [
        {
            name: "Artist",
            icon: <BrushIcon />,
            onClick: () => {
                setOpenArtistForm(true);
                handleCloseCreateMenu();
            },
        },
        {
            name: "Character",
            icon: <PersonIcon />,
            onClick: () => {
                setOpenCharacterForm(true);
                handleCloseCreateMenu();
            },
        },
        {
            name: "Submission",
            icon: <PhotoIcon />,
            onClick: () => {
                setOpenSubmissionForm(true);
                handleCloseCreateMenu();
            },
        },
        {
            name: "Tag",
            icon: <LocalOfferIcon />,
            onClick: () => {
                setOpenTagForm(true);
                handleCloseCreateMenu();
            },
        },
    ];

    return (
        <>
            <Tooltip title="Create">
                <IconButton onClick={handleOpenCreateMenu} sx={{ p: 0 }}>
                    <AddIcon />
                </IconButton>
            </Tooltip>
            <Menu
                sx={{ top: -80 }}
                id="menu-appbar"
                anchorEl={anchorCreateMenu}
                anchorOrigin={{
                    vertical: "top",
                    horizontal: "center",
                }}
                keepMounted
                transformOrigin={{
                    vertical: "top",
                    horizontal: "center",
                }}
                open={Boolean(anchorCreateMenu)}
                onClose={handleCloseCreateMenu}
                color="white"
            >

                {
                    MenuItems.map((item, index) => (
                        <MenuItem key={index} onClick={item.onClick}>
                            <Stack direction="row" spacing={2}>
                                {item.icon}
                                <Typography textAlign="center">{item.name}</Typography>
                            </Stack>
                        </MenuItem>
                    ))
                }

            </Menu>
            <SubmissionForm
                open={openSubmissionForm}
                setOpen={setOpenSubmissionForm}
            />
            <TagForm open={openTagForm} setOpen={setOpenTagForm} />
            <ArtistForm open={openArtistForm} setOpen={setOpenArtistForm} />
            <CharacterForm open={openCharacterForm} setOpen={setOpenCharacterForm} />
        </>
    );
}
