import { Grid, Typography, Rating, Stack, Button } from "@mui/material";
import TagList from "../TagList";
import Submission from "../../interfaces/Submission";
import CharacterList from "../CharacterList";
import ColorPalette from "../ColorPalette";
import ArtistList from "../ArtistList";

interface RightPanelProps {
  submission: Submission | undefined;
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
  return (
    <Grid container spacing={2} alignContent="center">
      <Grid item lg={12}>
        <img src={props.submission?.image} width="100%" />
        <ColorPalette colors={props.submission?.colors} />
      </Grid>
      <Grid item lg={12}>
        <Typography>{props.submission?.title}</Typography>
        <Typography>{props.submission?.description}</Typography>
        <Typography>Tags</Typography>
        <TagList tags={props.submission?.tags} />
        <Typography>Characters</Typography>
        <CharacterList characters={props.submission?.characters} />
        <Typography>Artist</Typography>
        <ArtistList artist={props.submission?.artist} />
      </Grid>
      <Grid item lg={12}>
        <Typography variant="h5">Information</Typography>
        <Grid container>
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
        <Stack>
          <Button>Edit</Button>
          <Button> Remove</Button>
        </Stack>
      </Grid>
    </Grid>
  );
}
