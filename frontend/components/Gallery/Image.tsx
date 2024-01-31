import { Grid, Paper, Rating, Typography } from "@mui/material";
import { motion } from "framer-motion";
import { useRouter } from "next/router";
import { RenderFunction } from "react-photo-album";
import { Settings, SubmissionPhoto } from "../../interfaces";
import { useAppContext } from "../../pages/_app";
import { formatDate } from "../../src/formatters";
import ArtistLabel from "../Artist/ArtistLabel";
import TagChipList from "../Tag/TagChipList";
interface ImageProps {
  photo: SubmissionPhoto;
  predefinedSettings?: Settings;
  // eslint-disable-next-line no-unused-vars
  renderDefaultPhoto: RenderFunction<void | { wrapped?: boolean | undefined; }>;
  wrapperStyle?: React.CSSProperties;
}

export default function Image({ photo, renderDefaultPhoto, wrapperStyle }: ImageProps) {
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
    <Paper style={wrapperStyle}

      elevation={0}
      sx={{
        display: "inline-block",
        cursor: "pointer",
        marginBottom: 2,
      }}
      component={motion.div}
      whileHover={{ scale: 1.02 }}
      transition={{ type: "spring", stiffness: 400, damping: 20 }}
      onClick={() => router.push(`/submission/${photo.submission.id}`)}
    >

      <Grid container >
        <Grid item xs={12} >
          {renderDefaultPhoto({ wrapped: true })}
        </Grid>
        <Grid item xs={12}>
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
                <Typography>{photo.submission.title}</Typography>
              </Grid>
            )}

            {settings.galleryInfo.date && (
              <Grid item>
                <Typography>{formatDate(photo.submission.date)}</Typography>
              </Grid>
            )}
            {settings.galleryInfo.rating && (
              <Grid item>
                <Rating value={photo.submission.rating} readOnly />
              </Grid>
            )}
            {settings.galleryInfo.dimensions && (
              <Grid item>
                <Typography>
                  {photo.submission.width}x{photo.submission.height}
                </Typography>
              </Grid>
            )}

            {settings.galleryInfo.artist && photo.submission.artist != undefined && (
              <>
                <Grid item xs={12}></Grid>
                <Grid item>
                  <ArtistLabel artist={photo.submission.artist} />
                </Grid>
              </>
            )}

            {settings.galleryInfo.tags && photo.submission.tags.length > 0 && (
              <Grid item>
                <TagChipList tags={photo.submission.tags} />
              </Grid>
            )}
          </Grid>
        </Grid>
      </Grid>
    </Paper>
  );
}
