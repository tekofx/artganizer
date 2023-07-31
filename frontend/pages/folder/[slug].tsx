import { useRouter } from 'next/router'
import { useEffect, useState, useContext } from 'react'
import { Icon, IconButton, Paper, Stack, Typography, Grid, Dialog, DialogTitle, TextField, DialogContent, Button } from '@mui/material'
import LocalOfferIcon from '@mui/icons-material/LocalOffer';
import EditIcon from '@mui/icons-material/Edit';
import CheckIcon from '@mui/icons-material/Check';
import ClearIcon from '@mui/icons-material/Clear';
import axios from 'axios'
import Folder from '../../interfaces/Folder'
import Gallery from '../../components/Gallery';
import Submission from '../../interfaces/Submission';
import { TwitterPicker } from 'react-color';
import FolderIcon from '@mui/icons-material/Folder';

import { DataContext } from "../_app";

export default function Page() {
    const [folder, setFolder] = useState<Folder>();
    const [submissions, setSubmissions] = useState<Submission[]>([]);
    const [open, setOpen] = useState(false);
    const [color, setColor] = useState('#fff');
    const [textFieldError, setTextFieldError] = useState<boolean>(false);
    const [newFolder, setNewFolder] = useState<string>("New Name");
    const { data, setData } = useContext(DataContext);


    const router = useRouter()

    const handleColorChange = (color: any, event: any) => {
        setColor(color.hex);
    };

    const onTextFieldChange = (event: any) => {
        setNewFolder(event.target.value);
        if (event.target.value == "") {
            setTextFieldError(true);
        } else {
            setTextFieldError(false);
        }
    };


    async function editLabel() {
        const response = await axios.put(`http://localhost:3001/folders/${router.query.slug}`, {
            name: newFolder,
        }, {
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json',
            },

        });
        setFolder(response.data);
        setOpen(false);
    }

    useEffect(() => {
        const slug = router.query.slug;
        if (slug) {
            var id = parseInt(slug.toString());

            // Get folder
            data.folders.filter((folder: Folder) => {
                if (folder.id == id) {
                    setFolder(folder);
                }
            });

            // TODO: Get submissions
        }
    }, [router.query.slug]);

    return (
        <Paper>
            <Grid container justifyContent="space-between" alignItems="center">
                <Grid item>
                    <Stack spacing={2} direction="row">
                        <FolderIcon style={{ fontSize: "4rem" }} />
                        <Typography variant="h1">
                            {folder?.name}
                        </Typography>
                    </Stack>
                </Grid>
                <Grid item>
                    <IconButton onClick={() => setOpen(true)}>
                        <EditIcon style={{ fontSize: "2rem" }} />
                    </IconButton>
                </Grid>
            </Grid>

            <Dialog open={open}>
                <DialogTitle>Edit folder</DialogTitle>
                <DialogContent>
                    <Stack spacing={2} direction="column">
                        <Typography>Name</Typography>
                        <TextField fullWidth
                            variant="outlined"
                            error={textFieldError}
                            helperText={textFieldError ? "Label name cannot be empty" : ""}
                            value={newFolder}
                            onChange={onTextFieldChange} />
                    </Stack>
                    <br />
                    <Stack spacing={2} direction="row" justifyContent="center">
                        <Button startIcon={<CheckIcon />} onClick={editLabel}>Save</Button>
                        <Button startIcon={<ClearIcon />} onClick={() => setOpen(false)}>Cancel</Button>
                    </Stack>
                </DialogContent>
            </Dialog>

            <Gallery />
        </Paper>
    )

}