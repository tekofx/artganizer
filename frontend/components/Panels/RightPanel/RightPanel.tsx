import ClearIcon from "@mui/icons-material/Clear";
import DoneIcon from "@mui/icons-material/Done";
import {
  Button,
  Dialog,
  DialogActions,
  DialogTitle,
  Stack,
  Typography,
} from "@mui/material";
import { useRouter } from "next/router";
import { Dispatch, SetStateAction, useState } from "react";
import Submission from "../../../interfaces/Submission";
import { useAppContext } from "../../../pages/_app";
import Edit from "./Edit";
import Info from "./Info";

interface RightPanelProps {
  submission: Submission;
  setSubmission: Dispatch<SetStateAction<Submission>>;
}

export default function RightPanel(props: RightPanelProps) {
  const { removeSubmission } = useAppContext();
  const router = useRouter();
  const [dialogOpen, setDialogOpen] = useState(false);
  const [editShow, setEditShow] = useState(false);

  async function onYesClick() {
    await removeSubmission(props.submission);
    router.push("/");
  }
  const handleClickOpenDialog = () => {
    setDialogOpen(true);
  };
  const handleCloseDialog = () => {
    setDialogOpen(false);
  };
  const toggleEdit = () => {
    setEditShow(!editShow);
  };

  return (
    <div
      style={{
        height: "100vh",
      }}
    >
      {!editShow ? (
        <>
          <Info
            submission={props.submission}
            toggleEdit={toggleEdit}
            handleClickOpenDialog={handleClickOpenDialog}
          />
        </>
      ) : (
        <>
          <Edit
            submission={props.submission}
            setEditShow={setEditShow}
            setSubmission={props.setSubmission}
          />
        </>
      )}

      <Dialog open={dialogOpen}>
        <DialogTitle>
          <Typography>
            Are you sure you want to remove this submission?
          </Typography>
        </DialogTitle>
        <DialogActions>
          <Stack direction="row" width="100%" spacing={2}>
            <Button
              variant="contained"
              size="small"
              startIcon={<DoneIcon />}
              onClick={onYesClick}
            >
              Yes
            </Button>
            <Button
              variant="contained"
              size="small"
              startIcon={<ClearIcon />}
              onClick={handleCloseDialog}
            >
              No
            </Button>
          </Stack>
        </DialogActions>
      </Dialog>
    </div>
  );
}
