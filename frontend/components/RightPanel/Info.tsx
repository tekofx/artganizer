import {
  Grid,
  Typography,
  Rating,
  Stack,
  Avatar,
  Container,
} from "@mui/material";
import TagList from "../TagList";
import Submission from "../../interfaces/Submission";
import CharacterList from "../CharacterList";
import ColorPalette from "../ColorPalette";
import { formatDate, convertBytes } from "../../src/formatters";

interface InfoProps {
  submission: Submission;
}
export default function Info(props: InfoProps) {
  return (
    <Container sx={{ paddingLeft: "2%" }}>
      <Grid container spacing={2} alignContent="center">
        <Grid item lg={12}>
          <Stack spacing={1}>
            <Typography variant="h4">{props.submission?.title}</Typography>
            <Typography variant="body1">
              {props.submission?.description}
            </Typography>
            <Typography>Rating</Typography>
            <Rating value={props.submission?.rating} readOnly />
            {props.submission?.tags && (
              <>
                <Typography>Tags</Typography>
                <TagList tags={props.submission?.tags} />
              </>
            )}
            {props.submission?.characters && (
              <>
                <Typography>Characters</Typography>
                <CharacterList characters={props.submission?.characters} />
              </>
            )}
            {props.submission?.artist && (
              <>
                <Typography>Artist</Typography>
                <Stack direction="row" alignItems="center" spacing={2}>
                  <Avatar
                    src={`http://localhost:3001/artists/uploads/${props.submission?.artist?.id}`}
                  />
                  <Typography>{props.submission?.artist?.name}</Typography>
                </Stack>
              </>
            )}
          </Stack>
        </Grid>
        <Grid item lg={12}>
          <ColorPalette colors={props.submission?.colors} />
        </Grid>
        <Grid item lg={12}>
          <Typography variant="h5">Image Information</Typography>
          <Grid container spacing={1}>
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
          <br />
        </Grid>
      </Grid>
    </Container>
  );
}
