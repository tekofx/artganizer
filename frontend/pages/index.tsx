import { useState, useEffect, useContext } from "react";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import Link from "../src/Link";
import axios from "axios";
import Image from "../interfaces/Image";
import Gallery from "../components/Gallery";
import Submission from "../interfaces/Submission";
import { DataContext } from "../pages/_app";
import Search from "../components/TopPanel/Search";
import TopPanel from "../components/TopPanel/TopPanel";

export default function Home() {
  const [submissions, setSubmissions] = useState<Submission[]>([]);
  const { data, setData } = useContext(DataContext);

  useEffect(() => {
    // Reset filters
    const newData = { ...data };
    newData.filters = {
      rating: -1,
      tags: [],
      folders: [],
      artists: [],
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
