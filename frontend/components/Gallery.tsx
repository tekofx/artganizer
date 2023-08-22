import { useState, useEffect, useContext } from "react";
import { motion } from "framer-motion";
import { Typography, Paper, Rating, Grid } from "@mui/material";
import Submission from "../interfaces/Submission";
import { DataContext, DataType } from "../pages/_app";
import { useRouter } from "next/router";
import Artist from "../interfaces/Artist";
import { formatDate } from "../src/formatters";
import TagList from "./Tag/TagList";
import ArtistLabel from "./Artist/ArtistLabel";
import Character from "../interfaces/Character";
interface GalleryProps {
  artist?: Artist;
  character?: Character;
}
interface ImageInfoProps {
  image: Submission;
  data: DataType;
}

function ImageInfo(props: ImageInfoProps) {
  function gallerySettingsAllFalse() {
    return (
      !props.data.settings.galleryInfo.artist &&
      !props.data.settings.galleryInfo.date &&
      !props.data.settings.galleryInfo.dimensions &&
      !props.data.settings.galleryInfo.rating &&
      !props.data.settings.galleryInfo.tags &&
      !props.data.settings.galleryInfo.title
    );
  }

  return (
    <Paper elevation={0} sx={{ display: "inline-block" }}>
      <img src={props.image.image} className="pic" />
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
        {props.data.settings.galleryInfo.title && (
          <Grid item xs={12}>
            <Typography>{props.image.title}</Typography>
          </Grid>
        )}

        {props.data.settings.galleryInfo.date && (
          <Grid item>
            <Typography>{formatDate(props.image.date)}</Typography>
          </Grid>
        )}
        {props.data.settings.galleryInfo.rating && (
          <Grid item>
            <Rating value={props.image.rating} readOnly />
          </Grid>
        )}
        {props.data.settings.galleryInfo.dimensions && (
          <Grid item>
            <Typography>
              {props.image.width}x{props.image.height}
            </Typography>
          </Grid>
        )}

        {props.data.settings.galleryInfo.artist &&
          props.image.artist != undefined && (
            <>
              <Grid item xs={12}></Grid>
              <Grid item>
                <ArtistLabel artist={props.image.artist} />
              </Grid>
            </>
          )}

        {props.data.settings.galleryInfo.tags &&
          props.image.tags.length > 0 && (
            <Grid item>
              <TagList tags={props.image.tags} />
            </Grid>
          )}
      </Grid>
    </Paper>
  );
}

export default function Gallery(props: GalleryProps) {
  const { data } = useContext(DataContext);
  const [submissions, setSubmissions] = useState<Submission[]>(
    data.submissions
  );
  const router = useRouter();

  useEffect(() => {
    let temp = data.submissions;
    if (props.artist != undefined) {
      temp = temp.filter(
        (submission) => submission.artist?.id == props.artist?.id
      );
    }

    if (props.character != undefined) {
      temp = temp.filter(
        (submission) =>
          submission.characters?.some(
            (character) => character.id == props.character?.id
          ) == true
      );
    }

    if (data.filters.rating > -1) {
      temp = temp.filter(
        (submission) => submission.rating >= data.filters.rating
      );
    }

    if (data.filters.tags.length > 0) {
      // Get all submissions that have at least one of the tags
      temp = temp.filter((submission) =>
        submission.tags?.some((tag) =>
          data.filters.tags.some((filterTag) => filterTag.id === tag.id)
        )
      );
    }

    if (data.filters.folders.length > 0) {
      temp = temp.filter((submission) =>
        submission.folders?.some((folder) =>
          data.filters.folders.some(
            (filterFolder) => filterFolder.id === folder.id
          )
        )
      );
    }
    if (data.filters.title.length > 0) {
      temp = temp.filter((submission) =>
        submission.title
          .toLowerCase()
          .includes(data.filters.title.toLowerCase())
      );
    }

    if (temp !== submissions) {
      setSubmissions(temp);
    }
  }, [
    data.filters.rating,
    data.filters.tags,
    data.filters.folders,
    data.filters.title,
    props.artist,
  ]);

  function ImageList() {
    return (
      <div>
        {submissions.map((image) => (
          <motion.div
            className="pics"
            whileHover={{ scale: 1.05 }}
            transition={{ type: "spring", stiffness: 400, damping: 20 }}
            onClick={() => router.push(`/submission/${image.id}`)}
            key={image.id}
          >
            <ImageInfo image={image} data={data} />
          </motion.div>
        ))}
      </div>
    );
  }

  return (
    <Paper
      sx={{ minHeight: "100vh", overflowY: "auto", maxHeight: "100vh", p: 2 }}
    >
      <div className="gallery">
        {submissions.length == 0 && (
          <Typography variant="h1">No submissions yet</Typography>
        )}
        <ImageList />
      </div>
    </Paper>
  );
}
