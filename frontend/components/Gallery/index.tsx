import { Paper, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import PhotoAlbum from "react-photo-album";
import { Artist, Character, SubmissionPhoto } from "../../interfaces";
import { useAppContext } from "../../pages/_app";
import { filterSubmissionPhotosByColor } from "../../src/colorManagement";
import Image from "./Image";

export default function Gallery({
  character,
  artist,
}: {
  character?: Character;
  artist?: Artist;
}) {
  const { submissions, filters } = useAppContext();

  const [photos, setPhotos] = useState<SubmissionPhoto[]>([]);


  useEffect(() => {
    var temp: SubmissionPhoto[] = submissions.map((submission) => ({
      src: submission.image,
      width: submission.width,
      height: submission.height,
      title: submission.title,
      submission: submission,

    }));

    if (character) {
      temp = temp.filter((photo: SubmissionPhoto) =>
        photo.submission.characters?.some((char) => char.id === character.id)
      );
    }

    if (artist) {
      temp = temp.filter(
        (photo: SubmissionPhoto) => photo.submission.artist?.id === artist.id
      );
    }

    if (filters.title) {
      temp = temp.filter((photo: SubmissionPhoto) =>
        photo.submission.title.toLowerCase().includes(filters.title.toLowerCase())
      );
    }

    if (filters.rating != -1) {
      temp = temp.filter(
        (photo: SubmissionPhoto) => photo.submission.rating >= filters.rating
      );
    }
    if (filters.tags.length > 0) {
      // Get all submissions that have at least one of the tags
      temp = temp.filter((photo: SubmissionPhoto) =>
        photo.submission.tags?.some((tag) =>
          filters.tags.some((filterTag) => filterTag.id === tag.id)
        )
      );
    }
    if (filters.characters.length > 0) {
      // Get all submissions that have at least one of the characters
      temp = temp.filter((photo: SubmissionPhoto) =>
        photo.submission.characters?.some((character) =>
          filters.characters.some(
            (filterCharacter) => filterCharacter.id === character.id
          )
        )
      );
    }
    if (filters.color != "") {
      temp = filterSubmissionPhotosByColor(temp, filters.color, 80);
    }
    if (filters.artists.length > 0) {
      temp = temp.filter((photo: SubmissionPhoto) =>
        filters.artists.some((artist) => artist.id === photo.submission.artist?.id)
      );
    }
    setPhotos(temp);
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
      {photos.length == 0 && (
        <Typography variant="h1">No submissions yet</Typography>
      )}
      <PhotoAlbum layout="rows" photos={photos} rowConstraints={{ singleRowMaxHeight: 300 }}

        targetRowHeight={(containerWidth: number) => {
          console.log(containerWidth)
          if (containerWidth <= 300) {
            return 250;
          }
          if (containerWidth <= 600) {
            return 250;
          }
          return 300;

        }}

        breakpoints={[300, 600, 1200]}


        renderPhoto={({ photo, wrapperStyle, renderDefaultPhoto }) => (
          <Image photo={photo} wrapperStyle={wrapperStyle} renderDefaultPhoto={renderDefaultPhoto} />

        )}
      />
    </Paper>
  );
}
