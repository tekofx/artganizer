import { CacheProvider, EmotionCache } from "@emotion/react";
import { Grid } from "@mui/material";
import CssBaseline from "@mui/material/CssBaseline";
import { ThemeProvider } from "@mui/material/styles";
import axios from "axios";
import { AppProps } from "next/app";
import Head from "next/head";
import { Dispatch, SetStateAction, createContext, useContext, useEffect, useState } from "react";
import LateralPanel from "../components/Panels/LeftPanel";
import { Artist, Character, Submission, Tag } from "../interfaces";
import createEmotionCache from "../src/createEmotionCache";
import theme from "../src/theme";
import "../styles/styles.css";
// Client-side cache, shared for the whole session of the user in the browser.
const clientSideEmotionCache = createEmotionCache();

if (process.env.API_URL == undefined) {
  process.env.API_URL = "http://localhost:3001";
}

export interface MyAppProps extends AppProps {
  emotionCache?: EmotionCache;
}

interface AppContextType {
  artists: Artist[];
  setArtists: Dispatch<SetStateAction<Artist[]>>;
  characters: Character[];
  setCharacters: Dispatch<SetStateAction<Character[]>>;
  tags: Tag[];
  setTags: Dispatch<SetStateAction<Tag[]>>;
  submissions: Submission[];
  setSubmissions: Dispatch<SetStateAction<Submission[]>>;
}

const AppContext = createContext<AppContextType | null>(null);


export default function MyApp(props: MyAppProps) {
  const { Component, emotionCache = clientSideEmotionCache, pageProps } = props;
  const [artists, setArtists] = useState<Artist[]>([]);
  const [submissions, setSubmissions] = useState<Submission[]>([]);
  const [characters, setCharacters] = useState<Character[]>([]);
  const [tags, setTags] = useState<Tag[]>([]);

  const getArtists = async () =>
    await axios.get(`${process.env.API_URL}/artists`).then((response) => {
      setArtists(response.data);
    });

  const getSubmissions = async () =>
    await axios.get(`${process.env.API_URL}/submissions`).then((response) => {
      setSubmissions(response.data);
    });

  const getCharacters = async () =>
    await axios.get(`${process.env.API_URL}/characters`).then((response) => {
      setCharacters(response.data);
    });

  const getTags = async () =>
    await axios.get(`${process.env.API_URL}/tags`).then((response) => {
      setTags(response.data);
    });

  useEffect(() => {
    getArtists();
    getSubmissions();
    getCharacters();
    getTags();
  }
    , []);

  return (
    <CacheProvider value={emotionCache}>
      <Head>
        <meta name="viewport" content="initial-scale=1, width=device-width" />
      </Head>
      <ThemeProvider theme={theme}>
        {/* CssBaseline kickstart an elegant, consistent, and simple baseline to build upon. */}
        <CssBaseline />
        <AppContext.Provider value={{ artists, setArtists, characters, setCharacters, tags, setTags, submissions, setSubmissions }}>
          <Grid container>
            <Grid item lg={2} position="fixed">
              <LateralPanel />
            </Grid>
            <Grid item lg={10} style={{ marginLeft: "16.66%" }}>
              <Component {...pageProps} />
            </Grid>
          </Grid>
        </AppContext.Provider>
      </ThemeProvider>
    </CacheProvider>
  );
}

export function useAppContext() {
  const context = useContext(AppContext);
  if (!context) {
    throw new Error('useAppContext must be used within an AppProvider');
  }
  return context;
}