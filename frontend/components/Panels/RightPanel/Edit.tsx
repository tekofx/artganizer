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
import { useState, Dispatch, SetStateAction, useContext } from "react";
import ArtistSelect from "../../Artist/ArtistSelect";
import Artist from "../../../interfaces/Artist";
import axios from "axios";
import DoneIcon from "@mui/icons-material/Done";
import TagSelect from "../../Tag/TagSelect";
import Tag from "../../../interfaces/Tag";
import { DataContext } from "../../../pages/_app";
import CharacterSelect from "../../Character/CharacterSelect";
import Character from "../../../interfaces/Character";
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

  const { setData } = useContext(DataContext);

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
            <TagSelect
              selectedTags={selectedTags}
              setSelectedTags={setSelectedTags}
            />
            <ArtistSelect
              selectedArtist={selectedArtist}
              setSelectedArtist={setSelectedArtist}
            />
            <CharacterSelect
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
