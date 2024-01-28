import Head from "next/head";
import Gallery from "../components/Gallery";
import TopPanel from "../components/Panels/TopPanel";

export default function Home() {
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
