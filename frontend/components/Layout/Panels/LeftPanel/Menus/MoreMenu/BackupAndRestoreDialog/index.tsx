import ClearIcon from "@mui/icons-material/Clear";
import CloudDownloadIcon from '@mui/icons-material/CloudDownload';
import RestoreIcon from '@mui/icons-material/Restore';
import {
    Button,
    Dialog,
    DialogContent,
    DialogTitle,
    Grid,
    IconButton,
    Stack,
    Typography
} from "@mui/material";
import { Dispatch, SetStateAction } from "react";
interface SettingsDialogProps {
    open: boolean;
    setOpen: Dispatch<SetStateAction<boolean>>;
}
export default function SettingsDialog(props: SettingsDialogProps) {

    return (
        <Dialog open={props.open} maxWidth={"xl"}>
            <DialogTitle>
                <Stack
                    direction="row"
                    alignItems="center"
                    spacing={2}
                    justifyContent="space-between"
                >
                    <Typography variant="h6">Settings</Typography>
                    <IconButton onClick={() => props.setOpen(false)}>
                        <ClearIcon />
                    </IconButton>
                </Stack>
            </DialogTitle>
            <DialogContent >
                <Grid container spacing={2}>
                    <Grid item>
                        <Button variant="contained" color="primary" startIcon={<CloudDownloadIcon />}>Backup</Button>
                    </Grid>
                    <Grid item>
                        <Button variant="contained" color="primary" startIcon={<RestoreIcon />}>Restore</Button>
                    </Grid>

                </Grid>
            </DialogContent>

        </Dialog>
    );
}
