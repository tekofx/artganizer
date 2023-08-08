import {
  Grid,
  Typography,
  Paper,
  Rating,
  Stack,
  Container,
} from "@mui/material";
import { useState, MouseEvent, useEffect, useContext } from "react";
import MenuIcon from "@mui/icons-material/Menu";
import AddIcon from "@mui/icons-material/Add";
import FolderIcon from "@mui/icons-material/Folder";
import LocalOfferIcon from "@mui/icons-material/LocalOffer";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import CheckIcon from "@mui/icons-material/Check";
import ClearIcon from "@mui/icons-material/Clear";
import PaletteIcon from "@mui/icons-material/Palette";
import { useRouter } from "next/router";
import axios from "axios";
import { DataContext } from "../../pages/_app";
import TagList from "../TagList";
import { TwitterPicker } from "react-color";
import Folder from "../../interfaces/Folder";
import { Tag } from "@mui/icons-material";
import Submission from "../../interfaces/Submission";
import CharacterList from "../CharacterList";
import ColorPalette from "../ColorPalette";
import ArtistList from "../ArtistList";
const settings = ["Profile", "Account", "Dashboard", "Logout"];

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

export default function RightPanel(props: RightPanelProps) {
  const [submission, setSubmission] = useState<Submission>();
  const { data, setData } = useContext(DataContext);

  const router = useRouter();

  return (
    <div>
      <Grid container spacing={2}>
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
          <Typography variant="h4">Information</Typography>
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
              Size
            </Grid>
            <Grid item lg={8}>
              <Typography>
                {props.submission?.width}x{props.submission?.height}
              </Typography>
            </Grid>

            <Grid item lg={4}>
              Format
            </Grid>
            <Grid item lg={8}>
              <Typography>{props.submission?.format.toUpperCase()}</Typography>
            </Grid>
          </Grid>
        </Grid>
      </Grid>
    </div>
  );
}
