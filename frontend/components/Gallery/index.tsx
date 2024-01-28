import { Paper, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import { Artist, Character } from "../../interfaces";
import Submission from "../../interfaces/Submission";
import { useAppContext } from "../../pages/_app";
import { filterSubmissionsByColor } from "../../src/colorManagement";
import Image from "./Image";

export default function Gallery({
  character,
  artist,
}: {
  character?: Character;
  artist?: Artist;
}) {
  const { submissions, filters } = useAppContext();

  const [gallerySubmissions, setGallerySubmissions] = useState<Submission[]>(
    []
  );

  useEffect(() => {
    console.log(filters);
    var temp = submissions;

    if (character) {
      temp = temp.filter((submission: Submission) =>
        submission.characters?.some((char) => char.id === character.id)
      );
      console.log(temp);
    }

    if (artist) {
      temp = temp.filter(
        (submission: Submission) => submission.artist?.id === artist.id
      );
    }

    if (filters.title) {
      temp = temp.filter((submission: Submission) =>
        submission.title.toLowerCase().includes(filters.title.toLowerCase())
      );
    }

    if (filters.rating != -1) {
      temp = temp.filter(
        (submission: Submission) => submission.rating >= filters.rating
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
    setGallerySubmissions(temp);
  }, [filters, submissions, character, artist]);

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
      {gallerySubmissions.length == 0 && (
        <Typography variant="h1">No submissions yet</Typography>
      )}
      <div style={{ columnCount: 3 }}>
        {gallerySubmissions.map((submission) => (
          <Image submission={submission} key={submission.id} />
        ))}
      </div>
    </Paper>
  );
}
