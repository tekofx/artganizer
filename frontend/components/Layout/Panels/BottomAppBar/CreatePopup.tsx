import AddIcon from "@mui/icons-material/Add";
import LocalOfferIcon from "@mui/icons-material/LocalOffer";
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
import { SubmissionForm, TagForm } from "../../../Forms";

export default function CreateMenu() {
    const [openSubmissionForm, setOpenSubmissionForm] = useState<boolean>(false);
    const [openTagForm, setOpenTagForm] = useState<boolean>(false);
    const [anchorCreateMenu, setAnchorCreateMenu] = useState<null | HTMLElement>(
        null
    );
    const handleCloseCreateMenu = () => {
        setAnchorCreateMenu(null);
    };
    const handleOpenCreateMenu = (event: MouseEvent<HTMLElement>) => {
        setAnchorCreateMenu(event.currentTarget);
    };

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
                <MenuItem
                    onClick={() => {
                        setOpenSubmissionForm(true);
                        handleCloseCreateMenu();
                    }}
                >
                    <Stack direction="row" spacing={2}>
                        <PhotoIcon />
                        <Typography textAlign="center">Submission</Typography>
                    </Stack>
                </MenuItem>
                <MenuItem
                    onClick={() => {
                        setOpenTagForm(true);
                        handleCloseCreateMenu();
                    }}
                >
                    <Stack direction="row" spacing={2}>
                        <LocalOfferIcon />
                        <Typography textAlign="center">Tag</Typography>
                    </Stack>
                </MenuItem>
            </Menu>
            <SubmissionForm
                open={openSubmissionForm}
                setOpen={setOpenSubmissionForm}
            />
            <TagForm open={openTagForm} setOpen={setOpenTagForm} />
        </>
    );
}
