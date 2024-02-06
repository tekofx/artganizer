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
import axios, { AxiosError, AxiosResponse } from "axios";
import { ChangeEvent, Dispatch, SetStateAction, useState } from "react";
import { AlertMessage } from "../../../../../../../interfaces";
import Snack from "../../../../../../Snack";
interface SettingsDialogProps {
    open: boolean;
    setOpen: Dispatch<SetStateAction<boolean>>;
}
export default function SettingsDialog(props: SettingsDialogProps) {

    const [alertMessage, setAlertMessage] = useState<AlertMessage>({ message: "", severity: "success" });
    const [selectedFile, setSelectedFile] = useState<File | null>(null); // eslint-disable-line
    const [open, setOpen] = useState(false);


    async function downloadBackup() {
        await axios.get("/api/settings/export").then(() => {
            console.log("Created backup");
        }
        ).catch((err) => {
            console.log(err);
        }
        );

        await axios.get("/api/settings/export/export.zip?download", { responseType: "blob" }).then((res: AxiosResponse) => {
            const url = window.URL.createObjectURL(new Blob([res.data]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', 'export.zip'); // o cualquier otro nombre de archivo
            document.body.appendChild(link);
            link.click();
            setAlertMessage({ message: "Backup downloaded", severity: "success" });
            setOpen(true);
        }
        ).catch((err) => {
            console.log(err);
            setAlertMessage({ message: "Error downloading backup", severity: "error" });
            setOpen(true);
        }
        );
    }

    async function onFileUpload(event: ChangeEvent<HTMLInputElement>) {
        if (!event.target.files) {
            return;
        }

        var file = event.target.files[0];
        const data = new FormData();
        data.append("backup", file);
        console.log("making request")
        await axios.post("/api/settings/import", data).then(() => {
            console.log("Backup restored");
            setAlertMessage({ message: "Backup restored", severity: "success" });
            setOpen(true);
        }
        ).catch((err: AxiosError) => {
            console.log(err.cause);
            setAlertMessage({ message: "Error restoring backup", severity: "error" });
            setOpen(true);
        }
        );
    }

    return (
        <Dialog open={props.open} maxWidth={"xl"}>
            <Snack
                open={open}
                setOpen={setOpen}
                alertMessage={alertMessage}
            />
            <DialogTitle>
                <Stack
                    direction="row"
                    alignItems="center"
                    spacing={2}
                    justifyContent="space-between"
                >
                    <Typography variant="h6">Backup and Restore</Typography>
                    <IconButton onClick={() => props.setOpen(false)}>
                        <ClearIcon />
                    </IconButton>
                </Stack>
            </DialogTitle>
            <DialogContent >
                <Grid container spacing={2}>
                    <Grid item>
                        <Button variant="contained" color="primary" startIcon={<CloudDownloadIcon />} onClick={downloadBackup}>Backup</Button>
                    </Grid>
                    <Grid item>
                        <Button variant="contained" component="label" color="primary" startIcon={<RestoreIcon />} >
                            Add restore file
                            <input type="file" hidden accept="application/zip" onChange={onFileUpload} />
                        </Button>
                    </Grid>

                </Grid>
            </DialogContent>

        </Dialog >
    );
}
