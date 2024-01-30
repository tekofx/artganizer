import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import DownloadIcon from "@mui/icons-material/Download";
import EditIcon from "@mui/icons-material/Edit";
import { Button, Grid, Paper, Rating, Stack, Typography } from "@mui/material";
import axios from "axios";
import Submission from "../../../../interfaces/Submission";
import { convertBytes } from "../../../../src/formatters";
import ArtistLabel from "../../../Artist/ArtistLabel";
import ColorPalette from "../../../Artist/ColorPalette";
import CharacterList from "../../../Character/CharacterList";
import TagList from "../../../Tag/TagChipList";
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
      .get(submission.original_image + "?download", { responseType: "blob" })
      .then((response) => {
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement("a");
        link.href = url;
        link.setAttribute("download", submission.filename);
        document.body.appendChild(link);
        link.click();
        link.remove();
      })
      .catch((err) => {
        console.log(err);
      });
  }

  return (
    <Grid
      container
      spacing={1}
      sx={{ paddingLeft: "0", maxHeight: "100vh", overflowY: "auto" }}
    >
      <Grid item xs={12}>
        <Paper elevation={0} sx={{ padding: 2 }}>
          <Grid container spacing={2} justifyContent="center">
            <Grid item>
              <Button
                variant="contained"
                startIcon={<DownloadIcon />}
                onClick={downloadImage}
              >
                Download
              </Button>
            </Grid>
            <Grid item>
              <Button
                variant="contained"
                startIcon={<EditIcon />}
                onClick={toggleEdit}
              >
                Edit
              </Button>
            </Grid>
            <Grid item>
              <Button
                variant="contained"
                startIcon={<DeleteForeverIcon />}
                onClick={handleClickOpenDialog}
              >
                Remove
              </Button>
            </Grid>
          </Grid>
        </Paper>
      </Grid>
      <Grid item xs={12}>
        <Paper elevation={0} sx={{ padding: 2 }}>
          <Stack spacing="1" sx={{ width: "100%" }}>
            <Typography variant="h4">{submission.title}</Typography>
            <Typography variant="body1">{submission.description}</Typography>
            <Typography>Rating</Typography>
            <Rating value={submission.rating} readOnly />
          </Stack>
        </Paper>
      </Grid>
      <Grid item xs={12}>
        {submission.tags && (
          <Paper elevation={0} sx={{ padding: 2 }}>
            <Stack spacing={1}>
              <Typography>Tags</Typography>
              <TagList />
            </Stack>
          </Paper>
        )}
      </Grid>
      <Grid item xs={12}>
        {submission.artist && (
          <Paper elevation={0} sx={{ padding: 2 }}>
            <Stack spacing={1}>
              <Typography>Artist</Typography>
              <ArtistLabel artist={submission.artist} clickable />
            </Stack>
          </Paper>
        )}
      </Grid>
      <Grid item xs={12}>
        {submission.characters && (
          <Paper elevation={0} sx={{ padding: 2 }}>
            <Stack spacing={1}>
              <Typography>Characters</Typography>
              <CharacterList characters={submission.characters} clickable />
            </Stack>
          </Paper>
        )}
      </Grid>
      <Grid item xs={12} lg={12}>
        <Paper elevation={0} sx={{ padding: 2 }}>
          <Stack spacing={1}>
            <Typography>Colors</Typography>
            <ColorPalette colors={submission.colors} />
          </Stack>
        </Paper>
      </Grid>
      <Grid item xs={12} lg={12}>
        <Paper elevation={0} sx={{ padding: 2 }}>
          <Typography variant="h5">Image Information</Typography>
          <Grid container spacing={1}>
            <Grid item lg={4}>
              Date
            </Grid>

            <Grid item lg={8}>
              <Typography>
                {new Date(submission.date).toLocaleDateString("es-ES", {
                  day: "2-digit",
                  month: "2-digit",
                  year: "numeric",
                })}
              </Typography>
            </Grid>

            <Grid item xs={12}>
              <Stack direction="row" spacing={2}>
                <Typography fontWeight={"bold"}>Dimensions</Typography>
                <Typography>
                  {submission.width}x{submission.height}
                </Typography>
              </Stack>
            </Grid>
            <Grid item xs={12}>
              <Stack direction="row" spacing={2} alignContent="space-between">
                <Typography fontWeight={"bold"}>Size</Typography>
                <Typography>{convertBytes(submission.size)}</Typography>
              </Stack>
            </Grid>
            <Grid item xs={12}>
              <Stack direction="row" spacing={2}>
                <Typography fontWeight={"bold"}>Format</Typography>
                <Typography>{submission.format.toUpperCase()}</Typography>
              </Stack>
            </Grid>
          </Grid>
        </Paper>
      </Grid>
    </Grid>
  );
}
