import { Grid, Typography } from "@mui/material";
import { Dispatch, SetStateAction } from "react";
import Artist from "../../../interfaces/Artist";
import Character from "../../../interfaces/Character";
import Tag from "../../../interfaces/Tag";
import ArtistAutocomplete from "../../Artist/ArtistAutocomplete";
import CharacterAutocomplete from "../../Character/CharacterAutocomplete";
import TagAutocomplete from "../../Tag/TagAutocomplete";

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
        <TagAutocomplete
          selectedTags={selectedTags}
          setSelectedTags={setSelectedTags}
        />
      </Grid>

      <Grid item xs={12}>

        <ArtistAutocomplete
          selectedArtist={selectedArtist}
          setSelectedArtist={setSelectedArtist}
        />
      </Grid>
      <Grid item xs={12}>

        <CharacterAutocomplete
          selectedCharacters={selectedCharacters}
          setSelectedCharacters={setSelectedCharacters}
        />
      </Grid>
    </Grid>
  );
}
