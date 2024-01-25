import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    IconButton,
    Stack,
    TextField,
    Typography,
} from "@mui/material";
import { Artist } from "../../../interfaces";
import Socials from "../../Forms/ArtistForm/Socials";
import SocialIcon from "../../SocialIcon";
import { ChangeEvent, ChangeEventHandler, useState } from "react";
import axios from "axios";
interface SocialsDialogProps {
    open: boolean;
    setOpen: any;
    artist: Artist;
    setArtist: any;
}

export default function AddSocialDialog({
    open,
    setOpen,
    artist,
    setArtist,
}: SocialsDialogProps) {
    const [social, setSocial] = useState({
        name: "",
        url: "",
    });
    function handleSocialURLChange(event: ChangeEvent<HTMLInputElement>) {
        setSocial({ ...social, url: event.target.value });
    }

    function handleSocialNameChange(event: ChangeEvent<HTMLInputElement>) {
        setSocial({ ...social, name: event.target.value });
    }

    async function onDoneClick() {

        await axios.post(process.env.API_URL + `/artists/${artist.id}/socials`, social).then((response) => {
            console.log(response.data);
        }
        ).catch((error) => {
            console.log(error);
        });
        const newSocials = [...artist.socials];
        newSocials.push(social);
        setArtist({ ...artist, socials: newSocials });
        setOpen(false);
    }

    return (
        <Dialog open={open}>
            <DialogTitle>
                <Typography variant="h6">Add Social</Typography>
            </DialogTitle>
            <DialogContent>
                <Stack direction="row" alignItems="center" spacing={2} >
                    <SocialIcon social={social} clickable={false} />
                    <TextField
                        label="Social Name"
                        value={social.name}
                        onChange={handleSocialNameChange}
                    />
                    <TextField
                        label="URL"
                        value={social.url}
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
