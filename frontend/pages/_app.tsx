import { useEffect, createContext, useState } from "react";
import Head from "next/head";
import { AppProps } from "next/app";
import { ThemeProvider } from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";
import { CacheProvider, EmotionCache } from "@emotion/react";
import theme from "../src/theme";
import createEmotionCache from "../src/createEmotionCache";
import { Grid } from "@mui/material";
import LateralPanel from "../components/LateralPanel/LateralPanel";
import Tag from "../interfaces/Tag";
import "../styles/styles.css";
import axios from "axios";
import Submission from "../interfaces/Submission";
import Folder from "../interfaces/Folder";
import Artist from "../interfaces/Artist";
// Client-side cache, shared for the whole session of the user in the browser.
const clientSideEmotionCache = createEmotionCache();

interface Filters {
  rating: number;
  tags: Tag[];
  folders: Folder[];
  artists: Artist[];
}

export interface MyAppProps extends AppProps {
  emotionCache?: EmotionCache;
  tags: Tag[];
  folders: Folder[];
  submissions: Submission[];
  artists: Artist[];
  filters: Filters;
}

// Inital props
MyApp.getInitialProps = async () => {
  const tagsResponse = await axios.get<Tag[]>("http://localhost:3001/tags", {
    headers: {
      "Access-Control-Allow-Origin": "*",
      "Content-Type": "application/json",
    },
  });
  const tags = tagsResponse.data;

  const foldersResponse = await axios.get<Folder[]>(
    "http://localhost:3001/folders",
    {
      headers: {
        "Access-Control-Allow-Origin": "*",
        "Content-Type": "application/json",
      },
    }
  );
  const folders = foldersResponse.data;

  const submissionsResponse = await axios.get<Submission[]>(
    "http://localhost:3001/submissions",
    {
      headers: {
        "Access-Control-Allow-Origin": "*",
        "Content-Type": "application/json",
      },
    }
  );
  const submissions = submissionsResponse.data;

  const artistsResponse = await axios.get<Submission[]>(
    "http://localhost:3001/artists",
    {
      headers: {
        "Access-Control-Allow-Origin": "*",
        "Content-Type": "application/json",
      },
    }
  );
  const artists = artistsResponse.data;
  console.log("Initial props");
  /* console.log(tags);
  console.log(folders);
  console.log(submissions);
  console.log(artists); */
  return { tags, folders, submissions, artists };
};

export interface DataType {
  tags: Tag[];
  folders: Folder[];
  submissions: Submission[];
  filters: Filters;
  artists: Artist[];
}

interface DataContextType {
  data: DataType;
  setData: (data: DataContextType["data"]) => void;
}

export const DataContext = createContext<DataContextType>({
  data: {
    tags: [],
    folders: [],
    submissions: [],
    filters: { rating: -1, tags: [], folders: [], artists: [] },
    artists: [],
  },
  setData: () => {},
});

export default function MyApp(props: MyAppProps) {
  const { Component, emotionCache = clientSideEmotionCache, pageProps } = props;
  const [data, setData] = useState<{
    tags: Tag[];
    folders: Folder[];
    submissions: Submission[];
    filters: Filters;
    artists: Artist[];
  }>({
    tags: props.tags,
    folders: props.folders,
    submissions: props.submissions,
    artists: props.artists,
    filters: props.filters || {
      rating: -1,
      tags: [],
      folders: [],
      artists: [],
    },
  });

  return (
    <CacheProvider value={emotionCache}>
      <Head>
        <meta name="viewport" content="initial-scale=1, width=device-width" />
      </Head>
      <ThemeProvider theme={theme}>
        {/* CssBaseline kickstart an elegant, consistent, and simple baseline to build upon. */}
        <CssBaseline />
        <DataContext.Provider value={{ data, setData }}>
          <Grid container>
            <Grid item lg={2}>
              <LateralPanel />
            </Grid>
            <Grid item lg={10}>
              <Component {...pageProps} />
            </Grid>
          </Grid>
        </DataContext.Provider>
      </ThemeProvider>
    </CacheProvider>
  );
}
