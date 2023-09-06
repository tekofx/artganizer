import {
  Typography,
  Stack,
  Button,
  Dialog,
  DialogTitle,
  DialogActions,
} from "@mui/material";
import Submission from "../../../interfaces/Submission";
import axios from "axios";
import { useRouter } from "next/router";
import { Dispatch, SetStateAction, useContext, useState } from "react";
import { DataContext } from "../../../pages/_app";
import ClearIcon from "@mui/icons-material/Clear";
import DoneIcon from "@mui/icons-material/Done";
import Info from "./Info";
import Edit from "./Edit";

interface RightPanelProps {
  submission: Submission;
  setSubmission: Dispatch<SetStateAction<Submission>>;
}

export default function RightPanel(props: RightPanelProps) {
  const router = useRouter();
  const { data, setData } = useContext(DataContext);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [editShow, setEditShow] = useState(false);

  async function removeSubmission() {
    var submission = props.submission;
    await axios.delete(process.env.API_URL + `/submissions/${submission.id}`);

    // Remove submission from data
    const newData = { ...data };
    newData.submissions = newData.submissions.filter(
      (sub: Submission) => sub.id != submission.id
    );
    setData(newData);

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
    <>
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
              onClick={removeSubmission}
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
    </>
  );
}
