import { useState, useEffect } from "react";
import { Typography, Paper } from "@mui/material";
import Submission from "../../interfaces/Submission";
import Image from "./Image";
import { filterSubmissionsByColor } from "../../src/colorManagement";
import { Filters } from "../../interfaces";
import axios from "axios";

export default function Gallery({ filters }: { filters: Filters }) {
  const [submissions, setSubmissions] = useState<Submission[]>(
    []
  );

  useEffect(() => {
    const getSubmissions = async () => {
      const res = await axios.get(process.env.API_URL + "/submissions");
      setSubmissions(res.data);
    }
    getSubmissions();
  }
    , []);

  useEffect(() => {
    var temp = submissions;

    if (filters.title) {
      temp = temp.filter((submission: Submission) =>
        submission.title
          .toLowerCase()
          .includes(filters.title.toLowerCase())
      );
    }

    if (filters.rating != -1) {
      temp = temp.filter(
        (submission: Submission) =>
          submission.rating >= filters.rating
      );
    }
    if (filters.tags.length > 0) {
      // Get all submissions that have at least one of the tags
      temp = temp.filter((submission: Submission) =>
        submission.tags?.some((tag) =>
          filters.tags.some((filterTag) => filterTag.id === tag.id)
        )
      );
    }
    if (filters.characters.length > 0) {
      // Get all submissions that have at least one of the characters
      temp = temp.filter((submission: Submission) =>
        submission.characters?.some((character) =>
          filters.characters.some(
            (filterCharacter) => filterCharacter.id === character.id
          )
        )
      );
    }
    if (filters.color != "") {
      temp = filterSubmissionsByColor(temp, filters.color, 80);
    }
    if (filters.artist != undefined) {
      temp = temp.filter(
        (submission: Submission) => submission.artist?.id == filters.artist?.id
      );
    }
    setSubmissions(temp);
  }, [filters, submissions]);

  return (
    <Paper
      sx={{
        minHeight: "100vh",
        overflowY: "auto",
        maxHeight: "100vh",
        p: 2,
        flexDirection: "column",
      }}
    >
      {submissions.length == 0 && (
        <Typography variant="h1">No submissions yet</Typography>
      )}
      <div style={{ columnCount: 3 }}>
        {submissions.map((image) => (
          <Image image={image} key={image.id} />
        ))}
      </div>
    </Paper>
  );
}
