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
    var temp = data.submissions;
    if (data.filters.rating > -1) {
      temp = submissions.filter(
        (submission) => submission.rating >= data.filters.rating
      );
    }

    if (data.filters.tags.length > 0) {
      temp = submissions.filter((submission) =>
        submission.tags?.some((tag) =>
          data.filters.tags.some((filterTag) => filterTag.id === tag.id)
        )
      );
    }

    if (data.filters.folders.length > 0) {
      temp = submissions.filter((submission) =>
        submission.folders?.some((folder) =>
          data.filters.folders.some(
            (filterFolder) => filterFolder.id === folder.id
          )
        )
      );
    }
    // FIXME: This is not working
    if (props.artist) {
      temp = submissions.filter(
        (submission) => submission.artist?.id === props.artist?.id
      );
    }

    if (temp !== submissions) {
      setSubmissions(temp);
    }
  }, [data.filters.rating, data.filters.tags, data.filters.folders]);

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
    <Paper sx={{ minHeight: "100vh" }}>
      <div className="gallery">
        {data.submissions.length == 0 && (
          <Stack
            direction="column"
            spacing={1}
            justifyContent="center"
            alignItems="center"
          >
            <Typography variant="h1">No submissions yet</Typography>
          </Stack>
        )}
        <ImageList />
      </div>
    </Paper>
  );
}
