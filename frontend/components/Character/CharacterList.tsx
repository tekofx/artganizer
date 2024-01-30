import { Typography } from "@mui/material";
import { Character } from "../../interfaces";
import { useAppContext } from "../../pages/_app";
import CharacterLabel from "./CharacterLabel";
interface CharacterListProps {
  search?: string;
  clickable?: boolean;
  charactersProp?: Character[];
}

export default function CharacterList({
  search,
  clickable,
  charactersProp,
}: CharacterListProps) {
  const { characters } = useAppContext();

  function getCharacters() {
    if (charactersProp == undefined) {
      return characters;
    }
    return charactersProp;
  }

  return (
    <div
      style={{
        maxHeight: "35vh",
        overflowY: "auto",
      }}
    >
      {characters.length == 0 && <Typography>No characters</Typography>}
      {getCharacters()
        .filter((artist) =>
          artist.name.toLowerCase().includes(search === undefined ? "" : search)
        )
        .map((character) => (
          <CharacterLabel
            key={character.id}
            character={character}
            clickable={clickable}
          />
        ))}
    </div>
  );
}
