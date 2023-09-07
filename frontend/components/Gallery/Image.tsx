import { Typography, Paper, Rating, Grid } from "@mui/material";
import Submission from "../../interfaces/Submission";
import { formatDate } from "../../src/formatters";
import TagList from "../Tag/TagList";
import ArtistLabel from "../Artist/ArtistLabel";
import { useContext } from "react";
import { DataContext } from "../../pages/_app";
import Settings from "../../interfaces/Settings";
import { useRouter } from "next/router";
import { motion } from "framer-motion";
interface ImageProps {
  image: Submission;
  width?: string;
  predefinedSettings?: Settings;
}

export default function Image({ image, width }: ImageProps) {
  const { data } = useContext(DataContext);
  const router = useRouter();

  function gallerySettingsAllFalse() {
    return (
      !data.settings.galleryInfo.artist &&
      !data.settings.galleryInfo.date &&
      !data.settings.galleryInfo.dimensions &&
      !data.settings.galleryInfo.rating &&
      !data.settings.galleryInfo.tags &&
      !data.settings.galleryInfo.title
    );
  }

  return (
    <Paper
      elevation={0}
      sx={{ display: "inline-block", width: width, cursor: "pointer" }}
      component={motion.div}
      whileHover={{ scale: 1.02 }}
      transition={{ type: "spring", stiffness: 400, damping: 20 }}
      onClick={() => router.push(`/submission/${image.id}`)}
    >
      <img src={image.image} className="pic" width="100%" />
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
        {data.settings.galleryInfo.title && (
          <Grid item xs={12}>
            <Typography>{image.title}</Typography>
          </Grid>
        )}

        {data.settings.galleryInfo.date && (
          <Grid item>
            <Typography>{formatDate(image.date)}</Typography>
          </Grid>
        )}
        {data.settings.galleryInfo.rating && (
          <Grid item>
            <Rating value={image.rating} readOnly />
          </Grid>
        )}
        {data.settings.galleryInfo.dimensions && (
          <Grid item>
            <Typography>
              {image.width}x{image.height}
            </Typography>
          </Grid>
        )}

        {data.settings.galleryInfo.artist && image.artist != undefined && (
          <>
            <Grid item xs={12}></Grid>
            <Grid item>
              <ArtistLabel artist={image.artist} />
            </Grid>
          </>
        )}

        {data.settings.galleryInfo.tags && image.tags.length > 0 && (
          <Grid item>
            <TagList tags={image.tags} />
          </Grid>
        )}
      </Grid>
    </Paper>
  );
}
