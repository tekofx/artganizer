import { useEffect, useState } from "react";
import Gallery from "../components/Gallery";
import TopPanel from "../components/Panels/TopPanel";
import Head from "next/head";
import { emptyFilters } from "../src/emptyEntities";
import { Filters } from "../interfaces";

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
