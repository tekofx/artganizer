import Head from "next/head";
import { useState } from "react";
import Gallery from "../components/Gallery";
import TopPanel from "../components/Panels/TopPanel";
import { Filters } from "../interfaces";
import { emptyFilters } from "../src/emptyEntities";

export default function Home() {
  const [filters, setFilters] = useState<Filters>(emptyFilters);


  return (
    <>
      <Head>
        <title>Artganizer</title>
      </Head>

      <TopPanel filters={filters} setFilters={setFilters} />
      <Gallery filters={filters} />
    </>
  );
}
