import { useRouter } from 'next/router'
import { useEffect, useState } from 'react'
import { Icon, IconButton, Paper, Stack, Typography, Grid, Dialog, DialogTitle, TextField, DialogContent, Button } from '@mui/material'
import LocalOfferIcon from '@mui/icons-material/LocalOffer';
import EditIcon from '@mui/icons-material/Edit';
import CheckIcon from '@mui/icons-material/Check';
import ClearIcon from '@mui/icons-material/Clear';
import axios from 'axios'
import Label from '../../interfaces/Label'
import Gallery from '../../components/Gallery';
import Submission from '../../interfaces/Submission';
import { TwitterPicker } from 'react-color';


export default function Page() {
    const [label, setLabel] = useState<Label>();
    const [submissions, setSubmissions] = useState<Submission[]>([]);
    const [open, setOpen] = useState(false);
    const [color, setColor] = useState('#fff');
    const [textFieldError, setTextFieldError] = useState<boolean>(false);
    const [newLabel, setNewLabel] = useState<string>("New Name");

    const router = useRouter()

    const handleColorChange = (color: any, event: any) => {
        setColor(color.hex);
    };

    const onTextFieldChange = (event: any) => {
        setNewLabel(event.target.value);
        if (event.target.value == "") {
            setTextFieldError(true);
        } else {
            setTextFieldError(false);
        }
    };


    async function editLabel() {
        const response = await axios.put(`http://localhost:3001/labels/${router.query.slug}`, {
            name: newLabel,
            color: color
        }, {
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json',
            },

        });
        setLabel(response.data);
        setOpen(false);
    }

    useEffect(() => {
        const slug = router.query.slug;
        if (slug) {

            axios.get(`http://localhost:3001/labels/${router.query.slug}`, {
                headers: {
                    'Access-Control-Allow-Origin': '*',
                    'Content-Type': 'application/json',
                },

            }).then((response) => {
                setLabel(response.data);
                setColor(response.data.color);
            }).catch((error) => {
                console.log(error);
            });

            axios.get('http://localhost:3001/submissions/', {
                headers: {
                    'Access-Control-Allow-Origin': '*',
                    'Content-Type': 'application/json',
                }, params: {
                    labels: [router.query.slug]
                }
            }).then((response) => {
                setSubmissions(response.data);
            }).catch((error) => {
                console.log(error);
            });
        }


    }, [router.query.slug]);

    return (
        <Paper>
            <Grid container justifyContent="space-between" alignItems="center">
                <Grid item>
                    <Stack spacing={2} direction="row">
                        <LocalOfferIcon sx={{ color: label?.color }} style={{ fontSize: "4rem" }} />
                        <Typography variant="h1">
                            {label?.name}
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
                <DialogTitle>Edit label</DialogTitle>
                <DialogContent>
                    <Stack spacing={2} direction="column">
                        <Typography>Name</Typography>
                        <TextField fullWidth
                            variant="outlined"
                            error={textFieldError}
                            helperText={textFieldError ? "Label name cannot be empty" : ""}
                            value={newLabel}
                            onChange={onTextFieldChange} />
                    </Stack>
                    <br />
                    <Stack spacing={2} direction="column">
                        <Typography>Color</Typography>
                        <Paper sx={{ backgroundColor: color, width: "100%", height: "2rem" }} />
                        <TwitterPicker triangle='hide' color={color} onChange={handleColorChange} />
                    </Stack>
                    <br />
                    <Stack spacing={2} direction="row" justifyContent="center">
                        <Button startIcon={<CheckIcon />} onClick={editLabel}>Save</Button>
                        <Button startIcon={<ClearIcon />} onClick={() => setOpen(false)}>Cancel</Button>
                    </Stack>
                </DialogContent>
            </Dialog>

            <Gallery submissions={submissions} />
        </Paper>
    )

}