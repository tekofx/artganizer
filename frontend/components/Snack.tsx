import { Dispatch, SetStateAction } from "react";
import { Alert, Snackbar } from "@mui/material";
import AlertMessage from "../interfaces/AlertMessage";

interface Props {
  open: boolean;
  setOpen: Dispatch<SetStateAction<boolean>>;
  alertMessage: AlertMessage;
}

export default function Snack(props: Props) {
  const handleClose = (
    event?: React.SyntheticEvent | Event,
    reason?: string
  ) => {
    if (reason === "clickaway") {
      return;
    }

    props.setOpen(false);
  };
  return (
    <Snackbar
      open={props.open}
      autoHideDuration={3000}
      onClose={handleClose}
      anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
      sx={{ zIndex: 200000 }}
    >
      <Alert
        onClose={handleClose}
        severity={props.alertMessage.severity}
        sx={{ width: "100%" }}
      >
        {props.alertMessage.message}
      </Alert>
    </Snackbar>
  );
}
