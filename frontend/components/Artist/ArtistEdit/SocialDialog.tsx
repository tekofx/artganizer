import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    Stack,
    TextField,
    Typography,
} from "@mui/material";
import axios from "axios";
import { ChangeEvent, Dispatch, SetStateAction, useState } from "react";
import { Artist, Social } from "../../../interfaces";
import SocialIcon from "../../SocialIcon";
interface SocialsDialogProps {
    open: boolean;
    setOpen: any;
    artist: Artist;
    setArtist: Dispatch<SetStateAction<Artist>>;
    social?: Social;
}

export default function SocialDialog({
    open,
    setOpen,
    artist,
    setArtist,
    social,

}: SocialsDialogProps) {

    const [socialDialog, setSocialDialog] = useState<Social>(social ? social : { name: "", url: "" });
    function handleSocialURLChange(event: ChangeEvent<HTMLInputElement>) {
        setSocialDialog({ ...socialDialog, url: event.target.value });
    }

    function handleSocialNameChange(event: ChangeEvent<HTMLInputElement>) {
        setSocialDialog({ ...socialDialog, name: event.target.value });
    }

    async function onDoneClick() {
        // Create social
        if (social == undefined) {
            await axios.post(process.env.API_URL + `/artists/${artist.id}/socials`, socialDialog).then((response) => {
                console.log(response.data);
                setArtist({ ...artist, socials: [...artist.socials, response.data] });
            }
            ).catch((error) => {
                console.log(error);
            });

        } else {
            // Edit social
            console.log("Edit social");
            await axios.put(process.env.API_URL + `/artists/${artist.id}/socials/${socialDialog.id}`, social).then((response) => {
                console.log(response.data);
                setArtist({ ...artist, socials: artist.socials.map((s) => s.id == socialDialog.id ? socialDialog : s) });
            }
            ).catch((error) => {
                console.log(error);
            });
        }
        setOpen(false);
    }

    return (
        <Dialog open={open}>
            <DialogTitle>
                <Typography variant="h6">Add Social</Typography>
            </DialogTitle>
            <DialogContent>
                <Stack direction="row" alignItems="center" spacing={2} >
                    <SocialIcon social={socialDialog} clickable={false} />
                    <TextField
                        label="Social Name"
                        value={socialDialog.name}
                        onChange={handleSocialNameChange}
                    />
                    <TextField
                        label="URL"
                        value={socialDialog.url}
                        onChange={handleSocialURLChange}
                    />

                </Stack>
            </DialogContent>
            <DialogActions>
                <Button onClick={() => setOpen(false)}>Cancel</Button>
                <Button onClick={onDoneClick}>
                    Done
                </Button>
            </DialogActions>
        </Dialog>
    );
}
