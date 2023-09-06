import { useEffect, useContext } from "react";
import Gallery from "../components/Gallery";
import { DataContext } from "../pages/_app";
import TopPanel from "../components/Panels/TopPanel";
import Head from "next/head";

export default function Home() {
  const { data, setData } = useContext(DataContext);

  useEffect(() => {
    // Reset filters
    const newData = { ...data };
    newData.filters = {
      rating: -1,
      tags: [],
      folders: [],
      artist: undefined,
      title: "",
      characters: [],
    };
    setData(newData);
  }, []);

  return (
    <>
      <Head>
        <title>ArtGanizer</title>
      </Head>

      <TopPanel />
      <Gallery />
    </>
  );
}
