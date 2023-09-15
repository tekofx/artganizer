import { Typography } from "@mui/material";
import Character from "../../interfaces/Character";
import CharacterLabel from "./CharacterLabel";
interface CharacterListProps {
  characters: Character[];
  clickable?: boolean;
}
function sortByName(artists: Character[]): Character[] {
  return artists.sort(function (a, b) {
    var textA = a.name.toUpperCase();
    var textB = b.name.toUpperCase();

    return textA < textB ? -1 : textA > textB ? 1 : 0;
  });
}
export default function CharacterList({
  characters,
  clickable,
}: CharacterListProps) {
  characters = sortByName(characters);
  return (
    <div
      style={{
        maxHeight: "35vh",
        overflowY: "auto",
      }}
    >
      {characters?.length == 0 && <Typography>No characters</Typography>}
      {characters?.map((character) => (
        <CharacterLabel
          key={character.id}
          character={character}
          clickable={clickable}
        />
      ))}
    </div>
  );
}
