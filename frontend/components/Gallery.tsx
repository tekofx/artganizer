import { useState, useEffect, useContext } from "react";
import { motion } from "framer-motion";
import { Typography, Paper, Stack } from "@mui/material";
import Submission from "../interfaces/Submission";
import { DataContext } from "../pages/_app";
import { useRouter } from "next/router";
import Artist from "../interfaces/Artist";

interface GalleryProps {
  artist?: Artist;
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
            <Stack
              direction="column"
              spacing={1}
              justifyContent="center"
              alignItems="center"
              sx={{ display: "inline-block" }}
            >
              <img src={image.image} className="pic" />
              <Typography>{image.title}</Typography>
              <Typography>
                {image.width}x{image.height}
              </Typography>
            </Stack>
          </motion.div>
        ))}
      </div>
    );
  }

  return (
    <Paper sx={{ minHeight: "100vh", overflowY: "auto", maxHeight: "100vh" }}>
      <div className="gallery">
        {submissions.length == 0 && (
          <Typography variant="h1">No submissions yet</Typography>
        )}
        <ImageList />
      </div>
    </Paper>
  );
}
