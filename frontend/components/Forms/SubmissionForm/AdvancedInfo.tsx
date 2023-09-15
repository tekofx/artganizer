import { Dispatch, SetStateAction } from "react";
import { Typography, Grid } from "@mui/material";
import Tag from "../../../interfaces/Tag";
import TagSelect from "../../Tag/TagSelect";
import ArtistSelect from "../../Artist/ArtistSelect";
import CharacterSelect from "../../Character/CharacterSelect";
import Character from "../../../interfaces/Character";
import Artist from "../../../interfaces/Artist";

interface Props {
  selectedTags: Tag[];
  setSelectedTags: Dispatch<SetStateAction<Tag[]>>;
  selectedArtist: Artist | undefined;
  setSelectedArtist: Dispatch<SetStateAction<Artist | undefined>>;
  selectedCharacters: Character[];
  setSelectedCharacters: Dispatch<SetStateAction<Character[]>>;
}
export default function AdvancedInfo({
  selectedTags,
  setSelectedTags,
  selectedArtist,
  setSelectedArtist,
  selectedCharacters,
  setSelectedCharacters,
}: Props) {
  return (
    <Grid container spacing={3}>
      <Grid item xs={12}>
        <Typography variant="h6">Advanced Info</Typography>
      </Grid>

      <Grid item xs={12}>
        <TagSelect
          selectedTags={selectedTags}
          setSelectedTags={setSelectedTags}
        />
      </Grid>

      <Grid item xs={12}>
        <ArtistSelect
          selectedArtist={selectedArtist}
          setSelectedArtist={setSelectedArtist}
        />
      </Grid>
      <Grid item xs={12}>
        <CharacterSelect
          selectedCharacters={selectedCharacters}
          setSelectedCharacters={setSelectedCharacters}
        />
      </Grid>
    </Grid>
  );
}
