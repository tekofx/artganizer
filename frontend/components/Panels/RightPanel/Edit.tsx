import {
  Grid,
  Typography,
  Rating,
  Stack,
  TextField,
  Button,
  Container,
} from "@mui/material";
import Submission from "../../../interfaces/Submission";
import { useState, Dispatch, SetStateAction } from "react";
import ArtistSelect from "../../Artist/ArtistSelect";
import Artist from "../../../interfaces/Artist";
import axios from "axios";
import DoneIcon from "@mui/icons-material/Done";
import Tag from "../../../interfaces/Tag";
import Character from "../../../interfaces/Character";
import TagAutocomplete from "../../Tag/TagAutocomplete";
import CharacterAutocomplete from "../../Character/CharacterAutocomplete";
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


  const [selectedArtist, setSelectedArtist] = useState<Artist | undefined>(
    props.submission?.artist
  );

  async function editSubmission() {
    submission.tags = selectedTags;
    submission.artist = selectedArtist;
    submission.characters = selectedCharacters;
    await axios
      .put(process.env.API_URL + `/submissions/${submission.id}`, {
        submission,
      })
      .then((response) => {
        props.setSubmission(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
    props.setEditShow(false);
  }

  return (
    <Container sx={{ paddingLeft: 1, overflowY: "auto", maxHeight: "95vh" }}>
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
            <TagAutocomplete
              selectedTags={selectedTags}
              setSelectedTags={setSelectedTags}
            />
            <ArtistSelect
              selectedArtist={selectedArtist}
              setSelectedArtist={setSelectedArtist}
            />
            <CharacterAutocomplete
              selectedCharacters={selectedCharacters}
              setSelectedCharacters={setSelectedCharacters}
            />
          </Stack>
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
