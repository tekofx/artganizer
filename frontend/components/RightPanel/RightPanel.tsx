import {
  Grid,
  Typography,
  Rating,
  Stack,
  Button,
  Dialog,
  DialogTitle,
  DialogActions,
} from "@mui/material";
import TagList from "../TagList";
import Submission from "../../interfaces/Submission";
import CharacterList from "../CharacterList";
import ColorPalette from "../ColorPalette";
import ArtistList from "../ArtistList";
import axios from "axios";
import { useRouter } from "next/router";
import { useContext, useState } from "react";
import { DataContext } from "../../pages/_app";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import ClearIcon from "@mui/icons-material/Clear";
import EditIcon from "@mui/icons-material/Edit";
import DoneIcon from "@mui/icons-material/Done";
interface RightPanelProps {
  submission: Submission;
}

function padTo2Digits(num: number) {
  return num.toString().padStart(2, "0");
}

function formatDate(date: Date | undefined) {
  if (date == undefined) {
    return "";
  }
  date = new Date(date);
  return (
    [
      padTo2Digits(date.getDate()),
      padTo2Digits(date.getMonth() + 1),
      date.getFullYear(),
    ].join("-") +
    " " +
    [
      padTo2Digits(date.getHours()),
      padTo2Digits(date.getMinutes()),
      padTo2Digits(date.getSeconds()),
    ].join(":")
  );
}

function convertBytes(bytes: number | undefined) {
  if (bytes == undefined) {
    return "unknown";
  }
  if (bytes > 1000000) {
    var mb = bytes / 1000000;
    return `${mb} MB`;
  } else {
    var kb = bytes / 1000;
    return `${kb} KB`;
  }
}

export default function RightPanel(props: RightPanelProps) {
  const router = useRouter();
  const { data, setData } = useContext(DataContext);
  const [open, setOpen] = useState(false);

  const handleClickOpen = () => {
    console.log(props.submission.rating);
    setOpen(true);
  };
  const handleClose = () => {
    setOpen(false);
  };

  async function removeSubmission() {
    var submission = props.submission;
    await axios.delete(`http://localhost:3001/submissions/${submission.id}`);

    // Remove submission from data
    const newData = { ...data };
    newData.submissions = newData.submissions.filter(
      (sub: Submission) => sub.id != submission.id
    );
    setData(newData);

    router.push("/");
  }

  return (
    <Grid container spacing={2} alignContent="center">
      <Grid item lg={12}>
        <Stack spacing={1}>
          <Typography variant="h4">{props.submission?.title}</Typography>
          <Typography variant="body1">
            {props.submission?.description}
          </Typography>
          <Typography>Tags</Typography>
          <TagList tags={props.submission?.tags} />
          <Typography>Characters</Typography>
          <CharacterList characters={props.submission?.characters} />
          <Typography>Artist</Typography>
          <ArtistList artist={props.submission?.artist} />
        </Stack>
      </Grid>
      <Grid item lg={12}>
        <ColorPalette colors={props.submission?.colors} />
      </Grid>
      <Grid item lg={12}>
        <Typography variant="h5">Information</Typography>
        <Grid container spacing={1}>
          <Grid item lg={4}>
            <Typography>Rating</Typography>
          </Grid>
          <Grid item lg={8}>
            <Rating value={props.submission?.rating} readOnly />
          </Grid>
          <Grid item lg={4}>
            Date
          </Grid>

          <Grid item lg={8}>
            <Typography>{formatDate(props.submission?.date)}</Typography>
          </Grid>

          <Grid item lg={4}>
            Dimensions
          </Grid>
          <Grid item lg={8}>
            <Typography>
              {props.submission?.width}x{props.submission?.height}
            </Typography>
          </Grid>
          <Grid item lg={4}>
            Size
          </Grid>
          <Grid item lg={8}>
            {convertBytes(props.submission?.size)}
          </Grid>

          <Grid item lg={4}>
            Format
          </Grid>
          <Grid item lg={8}>
            <Typography>{props.submission?.format.toUpperCase()}</Typography>
          </Grid>
        </Grid>
        <br/>
        <Grid item lg={12}>
          <Stack
            direction="row"
            width="100%"
            spacing={2}
            justifyContent="center"
          >
            <Button variant="contained" startIcon={<EditIcon />}>
              Edit
            </Button>

            <Button
              variant="contained"
              startIcon={<DeleteForeverIcon />}
              onClick={handleClickOpen}
            >
              Remove
            </Button>
          </Stack>
        </Grid>
      </Grid>
      <Dialog open={open}>
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
              onClick={handleClose}
            >
              No
            </Button>
          </Stack>
        </DialogActions>
      </Dialog>
    </Grid>
  );
}
