import DoneIcon from "@mui/icons-material/Done";
import {
  Button,
  Container,
  Grid,
  Paper,
  Rating,
  Stack,
  TextField,
  Typography,
} from "@mui/material";
import axios from "axios";
import { Dispatch, SetStateAction, useState } from "react";
import Artist from "../../../interfaces/Artist";
import Character from "../../../interfaces/Character";
import Submission from "../../../interfaces/Submission";
import Tag from "../../../interfaces/Tag";
import ArtistAutocomplete from "../../Artist/ArtistAutocomplete";
import CharacterAutocomplete from "../../Character/CharacterAutocomplete";
import TagAutocomplete from "../../Tag/TagAutocomplete";
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
    <Container sx={{ paddingLeft: "0", maxHeight: "100vh", overflowY: "auto" }}>
      <Grid container spacing={2} alignContent="center" sx={{ paddingTop: 3 }}>
        <Grid item lg={12}>
          <Stack spacing={1}>
            <Paper elevation={0} sx={{ padding: 2 }}>
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
              </Stack>

            </Paper>
            <Paper elevation={0} sx={{ padding: 2 }}>
              <TagAutocomplete
                selectedTags={selectedTags}
                setSelectedTags={setSelectedTags}
              />
            </Paper>
            <Paper elevation={0} sx={{ padding: 2 }}>
              <ArtistAutocomplete
                selectedArtist={selectedArtist}
                setSelectedArtist={setSelectedArtist}
              />
            </Paper>
            <Paper elevation={0} sx={{ padding: 2 }}>
              <CharacterAutocomplete
                selectedCharacters={selectedCharacters}
                setSelectedCharacters={setSelectedCharacters}
              />
            </Paper>
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
