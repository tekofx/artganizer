import { useEffect, useContext } from "react";
import Container from "@mui/material/Container";
import Box from "@mui/material/Box";
import Gallery from "../components/Gallery";
import { DataContext } from "../pages/_app";
import TopPanel from "../components/TopPanel/TopPanel";

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
    };
    setData(newData);
  }, []);

  return (
    <Container maxWidth={false}>
      <Box
        sx={{
          my: 1,
          display: "flex",
          flexDirection: "column",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <TopPanel />
        <Gallery />
      </Box>
    </Container>
  );
}
