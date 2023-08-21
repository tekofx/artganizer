import {
  Grid,
  Typography,
  Stack,
  Container,
  Paper,
  Button,
  Rating,
} from "@mui/material";
import TagList from "../Tag/TagList";
import Submission from "../../interfaces/Submission";
import CharacterList from "../CharacterList";
import ColorPalette from "../ColorPalette";
import { formatDate, convertBytes } from "../../src/formatters";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import EditIcon from "@mui/icons-material/Edit";
import ArtistLabel from "../Artist/ArtistLabel";
interface InfoProps {
  submission: Submission;
  toggleEdit: () => void;
  handleClickOpenDialog: () => void;
}
export default function Info(props: InfoProps) {
  return (
    <Container sx={{ paddingLeft: "0" }}>
      <Grid container spacing={2} alignContent="center">
        <Grid item lg={12}>
          <Stack spacing={1}>
            <Paper elevation={0} sx={{ padding: 2 }}>
              <Typography variant="h4">{props.submission.title}</Typography>
              <Typography variant="body1">
                {props.submission.description}
              </Typography>
              <Typography>Rating</Typography>
              <Rating value={props.submission.rating} readOnly />
            </Paper>
            {props.submission.tags && (
              <Paper elevation={0} sx={{ padding: 2 }}>
                <Typography>Tags</Typography>
                <TagList tags={props.submission.tags} />
              </Paper>
            )}
            {props.submission.characters && (
              <Paper elevation={0} sx={{ padding: 2 }}>
                <Typography>Characters</Typography>
                <CharacterList characters={props.submission.characters} />
              </Paper>
            )}
            {props.submission.artist && (
              <Paper elevation={0} sx={{ padding: 2 }}>
                <Typography>Artist</Typography>
                <ArtistLabel artist={props.submission.artist} clickable />
              </Paper>
            )}
          </Stack>
        </Grid>
        <Grid item lg={12}>
          <Paper elevation={0} sx={{ padding: 2 }}>
            <Typography>Colors</Typography>
            <ColorPalette colors={props.submission.colors} />
          </Paper>
        </Grid>
        <Grid item lg={12}>
          <Paper elevation={0} sx={{ padding: 2 }}>
            <Typography variant="h5">Image Information</Typography>
            <Grid container spacing={1}>
              <Grid item lg={4}>
                Date
              </Grid>

              <Grid item lg={8}>
                <Typography>{formatDate(props.submission.date)}</Typography>
              </Grid>

              <Grid item lg={4}>
                Dimensions
              </Grid>
              <Grid item lg={8}>
                <Typography>
                  {props.submission.width}x{props.submission.height}
                </Typography>
              </Grid>
              <Grid item lg={4}>
                Size
              </Grid>
              <Grid item lg={8}>
                {convertBytes(props.submission.size)}
              </Grid>

              <Grid item lg={4}>
                Format
              </Grid>
              <Grid item lg={8}>
                <Typography>{props.submission.format.toUpperCase()}</Typography>
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
              startIcon={<EditIcon />}
              onClick={props.toggleEdit}
            >
              Edit
            </Button>

            <Button
              variant="contained"
              startIcon={<DeleteForeverIcon />}
              onClick={props.handleClickOpenDialog}
            >
              Remove
            </Button>
          </Stack>
        </Grid>
      </Grid>
    </Container>
  );
}
