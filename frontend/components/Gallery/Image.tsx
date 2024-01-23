import { Typography, Paper, Rating, Grid } from "@mui/material";
import Submission from "../../interfaces/Submission";
import { formatDate } from "../../src/formatters";
import TagList from "../Tag/TagList";
import ArtistLabel from "../Artist/ArtistLabel";
import Settings from "../../interfaces/Settings";
import { useRouter } from "next/router";
import { motion } from "framer-motion";
import { defaultSettings } from "../../src/emptyEntities";
import { useEffect, useState } from "react";
import axios from "axios";
interface ImageProps {
  image: Submission;
  width?: string;
  predefinedSettings?: Settings;
}

export default function Image({ image, width }: ImageProps) {
  const router = useRouter();
  const [settings, setSettings] = useState<Settings>(defaultSettings);
  useEffect(() => {
    const getSettings = async () => {
      var res = await axios.get(process.env.API_URL + "/settings");
      setSettings(res.data);
    };
    getSettings();
  }
    , []);

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
      onClick={() => router.push(`/submission/${image.id}`)}
    >
      <img
        src={image.image}
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
            <Typography>{image.title}</Typography>
          </Grid>
        )}

        {settings.galleryInfo.date && (
          <Grid item>
            <Typography>{formatDate(image.date)}</Typography>
          </Grid>
        )}
        {settings.galleryInfo.rating && (
          <Grid item>
            <Rating value={image.rating} readOnly />
          </Grid>
        )}
        {settings.galleryInfo.dimensions && (
          <Grid item>
            <Typography>
              {image.width}x{image.height}
            </Typography>
          </Grid>
        )}

        {settings.galleryInfo.artist && image.artist != undefined && (
          <>
            <Grid item xs={12}></Grid>
            <Grid item>
              <ArtistLabel artist={image.artist} />
            </Grid>
          </>
        )}

        {settings.galleryInfo.tags && image.tags.length > 0 && (
          <Grid item>
            <TagList tags={image.tags} />
          </Grid>
        )}
      </Grid>
    </Paper>
  );
}
