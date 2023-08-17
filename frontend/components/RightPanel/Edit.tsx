import {
  Grid,
  Typography,
  Rating,
  Stack,
  TextField,
  Button,
  Container,
} from "@mui/material";
import Submission from "../../interfaces/Submission";
import CharacterList from "../CharacterList";
import ColorPalette from "../ColorPalette";
import { formatDate, convertBytes } from "../../src/formatters";
import { useState, Dispatch, SetStateAction } from "react";
import TagSelect from "../Forms/TagSelect";
import Tag from "../../interfaces/Tag";
import ArtistSelect from "../Forms/ArtistSelect";
import Artist from "../../interfaces/Artist";
import axios from "axios";
import EditIcon from "@mui/icons-material/Edit";

interface InfoProps {
  submission: Submission;
  setEditShow: Dispatch<SetStateAction<boolean>>;
  setSubmission: Dispatch<SetStateAction<Submission | undefined>>;
}
export default function Edit(props: InfoProps) {
  const [submission, setSubmission] = useState<Submission>(props.submission);
  const [selectedTags, setSelectedTags] = useState<Tag[]>(
    props.submission.tags
  );

  const [selectedArtist, setSelectedArtist] = useState<Artist | undefined>(
    props.submission?.artist
  );

  async function editSubmission() {
    submission.tags = selectedTags;
    submission.artist = selectedArtist;
    await axios
      .put(`http://localhost:3001/submissions/${submission.id}`, {
        submission,
      })
      .then((response) => {
        props.setSubmission(submission);
      })
      .catch((error) => {
        console.log(error);
      });
    props.setEditShow(false);
  }

  return (
    <Container sx={{ paddingLeft: 1 }}>
      <Grid container spacing={2} alignContent="center" sx={{ paddingTop: 3 }}>
        <Grid item lg={12}>
          <Stack spacing={1}>
            <TextField
              label="Title"
              value={submission?.title}
              onChange={(event) => {
                setSubmission((prevSubmission) => ({
                  ...prevSubmission,
                  title: event.target.value,
                }));
              }}
            />
            <TextField
              label="Description"
              multiline
              value={submission?.description}
              onChange={(event) => {
                setSubmission((prevSubmission) => ({
                  ...prevSubmission,
                  description: event.target.value,
                }));
              }}
            />
            <Typography>Rating</Typography>
            <Rating
              value={submission.rating}
              onChange={(event, newValue) => {
                setSubmission((prevSubmission) => ({
                  ...prevSubmission,
                  rating: newValue || 0,
                }));
              }}
            />
            <TagSelect
              selectedTags={selectedTags}
              setSelectedTags={setSelectedTags}
            />
            <Typography>Characters</Typography>
            <CharacterList characters={props.submission?.characters} />
            <ArtistSelect
              selectedArtist={selectedArtist}
              setSelectedArtist={setSelectedArtist}
            />
          </Stack>
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
        </Grid>
        <Grid item lg={12}>
          <Button
            variant="contained"
            startIcon={<EditIcon />}
            onClick={editSubmission}
          >
            Ok
          </Button>
        </Grid>
      </Grid>
    </Container>
  );
}