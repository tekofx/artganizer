import { Alert, Snackbar } from "@mui/material";
import { Dispatch, SetStateAction } from "react";
import AlertMessage from "../interfaces/AlertMessage";

interface Props {
  open: boolean;
  setOpen: Dispatch<SetStateAction<boolean>>;
  alertMessage: AlertMessage;
}

export default function Snack({ open, setOpen, alertMessage }: Props) {
  const handleClose = (
    event?: React.SyntheticEvent | Event,
    reason?: string
  ) => {
    if (reason === "clickaway") {
      return;
    }

    setOpen(false);
  };
  return (
    <Snackbar
      open={open}
      autoHideDuration={3000}
      onClose={handleClose}
      anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
      sx={{ zIndex: 200000 }}
    >
      <Alert
        onClose={handleClose}
        severity={alertMessage.severity}
        sx={{ width: "100%" }}
      >
        {alertMessage.message}
      </Alert>
    </Snackbar>
  );
}
