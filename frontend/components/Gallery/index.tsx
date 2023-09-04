import { useState, useEffect, useContext } from "react";
import { motion } from "framer-motion";
import { Typography, Paper } from "@mui/material";
import Submission from "../../interfaces/Submission";
import { DataContext } from "../../pages/_app";
import { useRouter } from "next/router";
import Artist from "../../interfaces/Artist";
import Character from "../../interfaces/Character";
import Image from "./Image";
interface GalleryProps {
  artist?: Artist;
  character?: Character;
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

    if (data.filters.artist != undefined) {
      temp = temp.filter(
        (submission) => submission.artist?.id == data.filters.artist?.id
      );
    }

    if (data.filters.characters.length > 0) {
      // Get all submissions that have at least one of the characters
      temp = temp.filter((submission) =>
        submission.characters?.some((char) =>
          data.filters.characters.some(
            (filterChar) => filterChar.id === char.id
          )
        )
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
    data.filters.artist,
    data.filters.characters,
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
            <Image image={image} />
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
