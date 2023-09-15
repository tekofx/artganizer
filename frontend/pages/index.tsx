import { useEffect, useContext } from "react";
import Gallery from "../components/Gallery";
import { DataContext } from "../pages/_app";
import TopPanel from "../components/Panels/TopPanel";
import Head from "next/head";
import { emptyFilters } from "../src/emptyEntities";

export default function Home() {
  const { data, setData } = useContext(DataContext);

  useEffect(() => {
    // Reset filters
    const newData = { ...data };
    newData.filters = emptyFilters;
    setData(newData);
  }, []);

  return (
    <>
      <Head>
        <title>Artganizer</title>
      </Head>

      <TopPanel />
      <Gallery />
    </>
  );
}
