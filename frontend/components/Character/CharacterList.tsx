import { Typography } from "@mui/material";
import { useAppContext } from "../../pages/_app";
import CharacterLabel from "./CharacterLabel";
interface CharacterListProps {
  search?: string;
  clickable?: boolean;
}

export default function CharacterList({
  search,
  clickable,
}: CharacterListProps) {
  const { characters } = useAppContext();
  return (
    <div
      style={{
        maxHeight: "35vh",
        overflowY: "auto",
      }}
    >
      {characters?.length == 0 && <Typography>No characters</Typography>}
      {characters
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
