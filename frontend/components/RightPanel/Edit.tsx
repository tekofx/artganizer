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
import { formatDate, convertBytes } from "../../src/formatters";
import { useState, Dispatch, SetStateAction } from "react";
import ArtistSelect from "../Artist/ArtistSelect";
import Artist from "../../interfaces/Artist";
import axios from "axios";
import DoneIcon from "@mui/icons-material/Done";
import TagSelect from "../Tag/TagSelect";
import Tag from "../../interfaces/Tag";
import { useContext } from "react";
import { DataContext } from "../../pages/_app";
import CharacterSelect from "../Character/CharacterSelect";
import Character from "../../interfaces/Character";
interface InfoProps {
  submission: Submission;
  setEditShow: Dispatch<SetStateAction<boolean>>;
  setSubmission: Dispatch<SetStateAction<Submission>>;
}
export default function Edit(props: InfoProps) {
  const [submission, setSubmission] = useState<Submission>(props.submission);
  const [selectedTags, setSelectedTags] = useState<Tag[]>(
    props.submission.tags
  );
  const [selectedCharacters, setSelectedCharacters] = useState<Character[]>(
    props.submission.characters
  );
  console.log(props.submission);

  const { setData } = useContext(DataContext);

  const [selectedArtist, setSelectedArtist] = useState<Artist | undefined>(
    props.submission?.artist
  );

  async function editSubmission() {
    console.log(submission);
    submission.tags = selectedTags;
    submission.artist = selectedArtist;
    submission.characters = selectedCharacters;
    await axios
      .put(`http://localhost:3001/submissions/${submission.id}`, {
        submission,
      })
      .then((response) => {
        console.log(response.data);
        props.setSubmission(response.data);
        setData((prevData) => ({
          ...prevData,
          submissions: prevData.submissions.map((submission) => {
            if (submission.id === response.data.id) {
              return response.data;
            } else {
              return submission;
            }
          }),
        }));
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
            <CharacterSelect
              selectedCharacters={selectedCharacters}
              setSelectedCharacters={setSelectedCharacters}
            />
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
            startIcon={<DoneIcon />}
            onClick={editSubmission}
          >
            Ok
          </Button>
        </Grid>
      </Grid>
    </Container>
  );
}
