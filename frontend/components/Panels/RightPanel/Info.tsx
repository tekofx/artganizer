import {
  Grid,
  Typography,
  Stack,
  Container,
  Paper,
  Button,
  Rating,
} from "@mui/material";
import TagList from "../../Tag/TagList";
import Submission from "../../../interfaces/Submission";
import CharacterList from "../../Character/CharacterList";
import ColorPalette from "../../Artist/ColorPalette";
import { convertBytes } from "../../../src/formatters";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import EditIcon from "@mui/icons-material/Edit";
import DownloadIcon from "@mui/icons-material/Download";
import ArtistLabel from "../../Artist/ArtistLabel";
import axios from "axios";
interface InfoProps {
  submission: Submission;
  toggleEdit: () => void;
  handleClickOpenDialog: () => void;
}
export default function Info({
  submission,
  toggleEdit,
  handleClickOpenDialog,
}: InfoProps) {
  async function downloadImage() {
    await axios
      .get(submission.image + "?download")
      .then((response) => {
        var format = submission.format;
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement("a");
        link.href = url;
        link.setAttribute("download", submission.title + "." + format);
        document.body.appendChild(link);
        link.click();
        link.remove();
      })
      .catch((err) => {
        console.log(err);
      });
  }

  return (
    <Container sx={{ paddingLeft: "0" }}>
      <Grid container spacing={2}>
        <Grid item lg={12}>
          <Stack spacing={1}>
            <Paper elevation={0} sx={{ padding: 2 }}>
              <Stack spacing="1">
                <Typography variant="h4">{submission.title}</Typography>
                <Typography variant="body1">
                  {submission.description}
                </Typography>
                <Typography>Rating</Typography>
                <Rating value={submission.rating} readOnly />
              </Stack>
            </Paper>
            {submission.tags && (
              <Paper elevation={0} sx={{ padding: 2 }}>
                <Stack spacing={1}>
                  <Typography>Tags</Typography>
                  <TagList tags={submission.tags} />
                </Stack>
              </Paper>
            )}
            {submission.characters && (
              <Paper elevation={0} sx={{ padding: 2 }}>
                <Stack spacing={1}>
                  <Typography>Characters</Typography>
                  <CharacterList characters={submission.characters} />
                </Stack>
              </Paper>
            )}
            {submission.artist && (
              <Paper elevation={0} sx={{ padding: 2 }}>
                <Stack spacing={1}>
                  <Typography>Artist</Typography>
                  <ArtistLabel artist={submission.artist} clickable />
                </Stack>
              </Paper>
            )}
          </Stack>
        </Grid>
        <Grid item lg={12}>
          <Paper elevation={0} sx={{ padding: 2 }}>
            <Stack spacing={1}>
              <Typography>Colors</Typography>
              <ColorPalette colors={submission.colors} />
            </Stack>
          </Paper>
        </Grid>
        <Grid item lg={12}>
          <Paper elevation={0} sx={{ padding: 2 }}>
            <Typography variant="h5">Image Information</Typography>
            <Grid container spacing={1}>
              {/* <Grid item lg={4}>
                Date
              </Grid>

              <Grid item lg={8}>
                <Typography>{submission.date.getUTCDate()}</Typography>
              </Grid> */}

              <Grid item lg={4}>
                Dimensions
              </Grid>
              <Grid item lg={8}>
                <Typography>
                  {submission.width}x{submission.height}
                </Typography>
              </Grid>
              <Grid item lg={4}>
                Size
              </Grid>
              <Grid item lg={8}>
                {convertBytes(submission.size)}
              </Grid>

              <Grid item lg={4}>
                Format
              </Grid>
              <Grid item lg={8}>
                <Typography>{submission.format.toUpperCase()}</Typography>
              </Grid>
            </Grid>
          </Paper>
        </Grid>
        <Grid item lg={12}>
          <Stack
            direction="row"
            width="100%"
            spacing={2}
            justifyContent="center"
          >
            <Button
              variant="contained"
              startIcon={<DownloadIcon />}
              onClick={downloadImage}
            >
              Download
            </Button>
            <Button
              variant="contained"
              startIcon={<EditIcon />}
              onClick={toggleEdit}
            >
              Edit
            </Button>

            <Button
              variant="contained"
              startIcon={<DeleteForeverIcon />}
              onClick={handleClickOpenDialog}
            >
              Remove
            </Button>
          </Stack>
        </Grid>
      </Grid>
    </Container>
  );
}
