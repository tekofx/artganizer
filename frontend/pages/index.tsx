import { Typography } from "@mui/material";
import Head from "next/head";
import { useState } from "react";
import Gallery from "../components/Gallery";
import TopPanel from "../components/Panels/TopPanel";
import { Filters } from "../interfaces";
import { emptyFilters } from "../src/emptyEntities";
import { useAppContext } from "./_app";

export default function Home() {
  const [filters, setFilters] = useState<Filters>(emptyFilters);
  const { artists, setArtists, characters, setCharacters } = useAppContext();

  return (
    <>
      <Head>
        <title>Artganizer</title>
      </Head>
      <TopPanel filters={filters} setFilters={setFilters} />
      {artists.map((artist) => {
        return <Typography key={artist.id}>{artist.name}</Typography>;
      })}

      {characters.map((character) => {
        return <Typography key={character.id}>{character.name}</Typography>;
      })}

      <Gallery filters={filters} />
    </>
  );
}
