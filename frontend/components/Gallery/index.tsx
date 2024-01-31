import { Paper, Typography } from "@mui/material";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import PhotoAlbum from "react-photo-album";
import { Artist, Character, SubmissionPhoto } from "../../interfaces";
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
  const { submissions, filters, settings } = useAppContext();
  const router = useRouter();

  const [gallerySubmissions, setGallerySubmissions] = useState<Submission[]>(
    []
  );
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




  var photos: SubmissionPhoto[] = submissions.map((submission) => ({
    src: submission.image,
    width: submission.width,
    height: submission.height,
    title: submission.title,
    submission: submission,

  }));

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
      <PhotoAlbum layout="rows" photos={photos} targetRowHeight={300}


        renderPhoto={({ photo, wrapperStyle, renderDefaultPhoto }) => (
          <Image photo={photo} wrapperStyle={wrapperStyle} renderDefaultPhoto={renderDefaultPhoto} />

        )}
      />
    </Paper>
  );
}
