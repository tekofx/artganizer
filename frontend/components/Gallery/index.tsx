import { useState, useEffect, useContext } from "react";
import { Typography, Paper } from "@mui/material";
import Submission from "../../interfaces/Submission";
import { DataContext } from "../../pages/_app";
import Artist from "../../interfaces/Artist";
import Character from "../../interfaces/Character";
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
      // FIXME: Make only 1 petition to the API using filters
      const res = await axios.get(process.env.API_URL + "/submissions");
      if (filters.title) {
        res.data = res.data.filter((submission: Submission) =>
          submission.title
            .toLowerCase()
            .includes(filters.title.toLowerCase())
        );
      }

      if (filters.rating != -1) {
        res.data = res.data.filter(
          (submission: Submission) =>
            submission.rating >= filters.rating
        );
      }
      if (filters.tags.length > 0) {
        // Get all submissions that have at least one of the tags
        res.data = res.data.filter((submission: Submission) =>
          submission.tags?.some((tag) =>
            filters.tags.some((filterTag) => filterTag.id === tag.id)
          )
        );
      }
      if (filters.characters.length > 0) {
        // Get all submissions that have at least one of the characters
        res.data = res.data.filter((submission: Submission) =>
          submission.characters?.some((character) =>
            filters.characters.some(
              (filterCharacter) => filterCharacter.id === character.id
            )
          )
        );
      }
      if (filters.color != "") {
        res.data = filterSubmissionsByColor(res.data, filters.color, 80);
      }
      if (filters.artist != undefined) {
        res.data = res.data.filter(
          (submission: Submission) => submission.artist?.id == filters.artist?.id
        );
      }
      setSubmissions(res.data);
    }
    getSubmissions();
  }, [filters]);

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
