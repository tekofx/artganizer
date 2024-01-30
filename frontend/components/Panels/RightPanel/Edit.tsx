import DoneIcon from "@mui/icons-material/Done";
import {
  Button,
  Container,
  Grid,
  Paper,
  Rating,
  Stack,
  Typography
} from "@mui/material";
import { Dispatch, SetStateAction, useState } from "react";
import Artist from "../../../interfaces/Artist";
import Character from "../../../interfaces/Character";
import Submission from "../../../interfaces/Submission";
import Tag from "../../../interfaces/Tag";
import { useAppContext } from "../../../pages/_app";
import ArtistAutocomplete from "../../Artist/ArtistAutocomplete";
import CharacterAutocomplete from "../../Character/CharacterAutocomplete";
import LimitedTextField from "../../LimitedTextField";
import TagAutocomplete from "../../Tag/TagAutocomplete";
interface InfoProps {
  submission: Submission;
  setEditShow: Dispatch<SetStateAction<boolean>>;
  setSubmission: Dispatch<SetStateAction<Submission>>;
}
export default function Edit(props: InfoProps) {
  const { editSubmission } = useAppContext();
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

  async function onOkButton() {
    submission.tags = selectedTags;
    submission.artist = selectedArtist;
    submission.characters = selectedCharacters;

    var result = await editSubmission(submission);
    if (result) {
      setSubmission(result);
      props.setEditShow(false);
      props.setSubmission(result);
    }
  }

  return (
    <Container sx={{ paddingLeft: "0", maxHeight: "100vh", overflowY: "auto" }}>
      <Grid container spacing={2} alignContent="center" sx={{ paddingTop: 3 }}>
        <Grid item lg={12}>
          <Stack spacing={1}>
            <Paper elevation={0} sx={{ padding: 2 }}>
              <Stack spacing={1}>


                <LimitedTextField
                  maxLength={50}
                  label="Title"
                  value={submission?.title}
                  onChange={(event) => {
                    setSubmission((prevSubmission) => ({
                      ...prevSubmission,
                      title: event.target.value,
                    }));
                  }}
                />
                <LimitedTextField
                  maxLength={500}
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
            onClick={onOkButton}
          >
            Ok
          </Button>
        </Grid>
      </Grid>
    </Container>
  );
}
