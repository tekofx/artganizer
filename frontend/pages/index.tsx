import { useEffect, useContext, useState } from "react";
import Gallery from "../components/Gallery";
import { DataContext } from "../pages/_app";
import TopPanel from "../components/Panels/TopPanel";
import Head from "next/head";
import { emptyFilters } from "../src/emptyEntities";
import { Filters } from "../interfaces";

export default function Home() {
  //const { data, setData } = useContext(DataContext);
  const [filters, setFilters] = useState<Filters>(emptyFilters);

  useEffect(() => {
    // Reset filters
    /* const newData = { ...data };
    newData.filters = emptyFilters;
    setData(newData); */
  }, []);

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
