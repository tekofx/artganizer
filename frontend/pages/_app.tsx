import { createContext, useState } from "react";
import Head from "next/head";
import { AppProps } from "next/app";
import { ThemeProvider } from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";
import { CacheProvider, EmotionCache } from "@emotion/react";
import theme from "../src/theme";
import createEmotionCache from "../src/createEmotionCache";
import { Grid } from "@mui/material";
import LateralPanel from "../components/Panels/LeftPanel";
import "../styles/styles.css";
import axios from "axios";
import {
  Submission,
  Artist,
  Filters,
  Settings,
  Tag,
  DataContextType,
} from "../interfaces";
import { defaultSettings } from "../src/emptyEntities";
import Character from "../interfaces/Character";
// Client-side cache, shared for the whole session of the user in the browser.
const clientSideEmotionCache = createEmotionCache();

if (process.env.API_URL == undefined) {
  process.env.API_URL = "http://localhost:3001";
}

export interface MyAppProps extends AppProps {
  emotionCache?: EmotionCache;
  tags: Tag[];
  submissions: Submission[];
  artists: Artist[];
  characters: Character[];
  filters: Filters;
  settings: Settings;
}

// Inital props
MyApp.getInitialProps = async () => {
  const tagsResponse = await axios.get<Tag[]>(process.env.API_URL + "/tags", {
    headers: {
      "Access-Control-Allow-Origin": "*",
      "Content-Type": "application/json",
    },
  });
  const tags = tagsResponse.data;

  const submissionsResponse = await axios.get<Submission[]>(
    process.env.API_URL + "/submissions",
    {
      headers: {
        "Access-Control-Allow-Origin": "*",
        "Content-Type": "application/json",
      },
    }
  );
  const submissions = submissionsResponse.data;

  const artistsResponse = await axios.get<Submission[]>(
    process.env.API_URL + "/artists",
    {
      headers: {
        "Access-Control-Allow-Origin": "*",
        "Content-Type": "application/json",
      },
    }
  );
  const artists = artistsResponse.data;

  const settingsResponse = await axios.get<Settings>(
    process.env.API_URL + "/settings",
    {
      headers: {
        "Access-Control-Allow-Origin": "*",
        "Content-Type": "application/json",
      },
    }
  );
  const settings = settingsResponse.data;

  const charactersResponse = await axios.get<Character[]>(
    process.env.API_URL + "/characters",
    {
      headers: {
        "Access-Control-Allow-Origin": "*",
        "Content-Type": "application/json",
      },
    }
  );
  const characters = charactersResponse.data;

  return { tags, submissions, artists, settings, characters };
};

export const DataContext = createContext<DataContextType>({
  data: {
    tags: [],
    submissions: [],
    filters: {
      rating: -1,
      tags: [],
      folders: [],
      artist: undefined,
      title: "",
      characters: [],
      color: "",
    },
    artists: [],
    characters: [],
    settings: defaultSettings,
  },
  setData: () => {},
});

export default function MyApp(props: MyAppProps) {
  const { Component, emotionCache = clientSideEmotionCache, pageProps } = props;
  const [data, setData] = useState<{
    tags: Tag[];
    submissions: Submission[];
    filters: Filters;
    artists: Artist[];
    characters: Character[];
    settings: Settings;
  }>({
    tags: props.tags,
    submissions: props.submissions,
    artists: props.artists,
    characters: props.characters,
    filters: props.filters || {
      rating: -1,
      tags: [],
      folders: [],
      artists: [],
      characters: [],
      title: "",
      color: "",
    },
    settings: props.settings,
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
            <Grid item lg={2} position="fixed">
              <LateralPanel />
            </Grid>
            <Grid item lg={10} style={{ marginLeft: "16.66%" }}>
              <Component {...pageProps} />
            </Grid>
          </Grid>
        </DataContext.Provider>
      </ThemeProvider>
    </CacheProvider>
  );
}
