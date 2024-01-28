import { Grid, Paper, Rating, Typography } from "@mui/material";
import { motion } from "framer-motion";
import { useRouter } from "next/router";
import { Settings } from "../../interfaces";
import Submission from "../../interfaces/Submission";
import { useAppContext } from "../../pages/_app";
import { formatDate } from "../../src/formatters";
import ArtistLabel from "../Artist/ArtistLabel";
import TagList from "../Tag/TagList";
interface ImageProps {
  submission: Submission;
  width?: string;
  predefinedSettings?: Settings;
}

export default function Image({ submission, width }: ImageProps) {
  const router = useRouter();
  const { settings } = useAppContext();

  function gallerySettingsAllFalse() {
    return (
      !settings.galleryInfo.artist &&
      !settings.galleryInfo.date &&
      !settings.galleryInfo.dimensions &&
      !settings.galleryInfo.rating &&
      !settings.galleryInfo.tags &&
      !settings.galleryInfo.title
    );
  }

  return (
    <Paper
      elevation={0}
      sx={{
        display: "inline-block",
        width: width,
        cursor: "pointer",
        marginBottom: 2,
      }}
      component={motion.div}
      whileHover={{ scale: 1.02 }}
      transition={{ type: "spring", stiffness: 400, damping: 20 }}
      onClick={() => router.push(`/submission/${submission.id}`)}
    >
      <img
        src={submission.image}
        style={{
          width: "100%",
          height: "auto",
        }}
      />
      <Grid
        container
        spacing={1}
        alignContent="center"
        sx={{
          paddingBottom: 2,
          paddingLeft: 2,
          paddingRight: 2,
          display: gallerySettingsAllFalse() == true ? "none" : "block",
        }}
      >
        {settings.galleryInfo.title && (
          <Grid item xs={12}>
            <Typography>{submission.title}</Typography>
          </Grid>
        )}

        {settings.galleryInfo.date && (
          <Grid item>
            <Typography>{formatDate(submission.date)}</Typography>
          </Grid>
        )}
        {settings.galleryInfo.rating && (
          <Grid item>
            <Rating value={submission.rating} readOnly />
          </Grid>
        )}
        {settings.galleryInfo.dimensions && (
          <Grid item>
            <Typography>
              {submission.width}x{submission.height}
            </Typography>
          </Grid>
        )}

        {settings.galleryInfo.artist && submission.artist != undefined && (
          <>
            <Grid item xs={12}></Grid>
            <Grid item>
              <ArtistLabel artist={submission.artist} />
            </Grid>
          </>
        )}

        {settings.galleryInfo.tags && submission.tags.length > 0 && (
          <Grid item>
            <TagList tags={submission.tags} />
          </Grid>
        )}
      </Grid>
    </Paper>
  );
}
