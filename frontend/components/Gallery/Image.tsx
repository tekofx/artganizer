import { Typography, Paper, Rating, Grid } from "@mui/material";
import Submission from "../../interfaces/Submission";
import { formatDate } from "../../src/formatters";
import TagList from "../Tag/TagList";
import ArtistLabel from "../Artist/ArtistLabel";
import { useContext } from "react";
import { DataContext } from "../../pages/_app";
interface ImageProps {
  image: Submission;
}

export default function Image({ image }: ImageProps) {
  const { data } = useContext(DataContext);
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
    <Paper elevation={0} sx={{ display: "inline-block" }}>
      <img src={image.image} className="pic" />
      <Grid
        container
        spacing={1}
        justifyContent="center"
        alignItems="center"
        sx={{
          paddingBottom: 2,
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
