import { Button, Dialog, DialogActions, DialogContent, Typography } from "@mui/material";
import { useRouter } from "next/router";
import { Dispatch, SetStateAction } from "react";
import Cookies from "universal-cookie";
var cookies = new Cookies();

interface LogOutProps {
    open: boolean;
    setOpen: Dispatch<SetStateAction<boolean>>;
}

export default function LogOut({ open, setOpen }: LogOutProps) {
    const router = useRouter();

    function logOut() {
        cookies.remove("TOKEN");
        setOpen(false);
        router.push('/login');

        // log out
    }


    return (
        <Dialog open={open}>
            <DialogContent>
                <Typography>Do you want to log out?</Typography>
            </DialogContent>
            <DialogActions>
                <Button onClick={() => setOpen(false)}>Cancel</Button>
                <Button onClick={logOut}>Log Out</Button>
            </DialogActions>
        </Dialog>
    )
}