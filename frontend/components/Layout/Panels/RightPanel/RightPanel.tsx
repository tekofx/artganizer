import ClearIcon from "@mui/icons-material/Clear";
import DoneIcon from "@mui/icons-material/Done";
import {
  Button,
  Dialog,
  DialogActions,
  DialogTitle,
  Grid,
  Paper,
  Stack,
  Typography,
} from "@mui/material";
import { useRouter } from "next/router";
import { Dispatch, SetStateAction, useState } from "react";
import Submission from "../../../../interfaces/Submission";
import { useAppContext } from "../../../../pages/_app";
import Edit from "./Edit";
import Info from "./Info";

interface RightPanelProps {
  submission: Submission;
  setSubmission: Dispatch<SetStateAction<Submission>>;
}

export default function RightPanel(props: RightPanelProps) {
  const { removeSubmission, isMobile } = useAppContext();
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
    <Paper
      elevation={0}
      style={{
        height: isMobile ? "auto" : "100%",
        overflowY: "scroll",
      }}
    >
      <Grid container>
        {!editShow ? (
          <Grid item xs={12}>
            <Info
              submission={props.submission}
              toggleEdit={toggleEdit}
              handleClickOpenDialog={handleClickOpenDialog}
            />
          </Grid>
        ) : (
          <Grid item xs={12}>
            <Edit
              submission={props.submission}
              setEditShow={setEditShow}
              setSubmission={props.setSubmission}
            />
          </Grid>
        )}
      </Grid>

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
    </Paper>
  );
}
