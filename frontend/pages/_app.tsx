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
import { handleCreateArtist, handleEditArtist, handleRemoveArtist } from "../src/api/artists";
import { handleCreateCharacter, handleEditCharacter, handleRemoveCharacter } from "../src/api/characters";
import { handleCreateSubmission } from "../src/api/submissions";
import { handleCreateTag } from "../src/api/tags";
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
  // eslint-disable-next-line no-unused-vars
  createSubmission(submission: Submission, selectedTags: Tag[], selectedArtist: Artist | undefined, selectedCharacters: Character[]): Promise<Submission | undefined>;
  // eslint-disable-next-line no-unused-vars
  createArtist(artist: Artist): Promise<Artist | undefined>;
  // eslint-disable-next-line no-unused-vars
  createCharacter(character: Character): Promise<Character | undefined>;
  // eslint-disable-next-line no-unused-vars
  createTag(tag: Tag): Promise<Tag | undefined>;
  // eslint-disable-next-line no-unused-vars
  editArtist(artist: Artist): Promise<Artist | undefined>;
  // eslint-disable-next-line no-unused-vars
  editCharacter(character: Character): Promise<Character | undefined>;
  // eslint-disable-next-line no-unused-vars
  removeArtist(artist: Artist): Promise<boolean | undefined>;
  // eslint-disable-next-line no-unused-vars
  removeCharacter(character: Character): Promise<boolean | undefined>;

}

const AppContext = createContext<AppContextType | null>(null);


export default function MyApp(props: MyAppProps) {
  const { Component, emotionCache = clientSideEmotionCache, pageProps } = props;
  const [artists, setArtists] = useState<Artist[]>([]);
  const [submissions, setSubmissions] = useState<Submission[]>([]);
  const [characters, setCharacters] = useState<Character[]>([]);
  const [tags, setTags] = useState<Tag[]>([]);

  async function createSubmission(submission: Submission, selectedTags: Tag[], selectedArtist: Artist | undefined, selectedCharacters: Character[]) {
    const submissionCreated = await handleCreateSubmission(submission, selectedTags, selectedArtist, selectedCharacters);
    if (submissionCreated) {
      setSubmissions([...submissions, submissionCreated]);
    }
    return submissionCreated;
  }

  async function createArtist(artist: Artist) {
    const artistCreated = await handleCreateArtist(artist);
    if (artistCreated) {
      setArtists([...artists, artistCreated]);
    }
    return artistCreated;
  }

  async function editArtist(artist: Artist) {
    const artistEdited = await handleEditArtist(artist);
    if (artistEdited) {
      setArtists([...artists.filter(a => a.id != artist.id), artistEdited]);
    }
    getArtists();
    return artistEdited;
  }

  async function removeArtist(artist: Artist) {
    const status = await handleRemoveArtist(artist);
    if (status) {
      setArtists([...artists.filter(a => a.id != artist.id)]);
    }
    return status;
  }

  async function createCharacter(character: Character) {
    const characterCreated = await handleCreateCharacter(character);
    if (characterCreated) {
      setCharacters([...characters, characterCreated]);
    }
    return characterCreated;
  }

  async function editCharacter(character: Character) {
    const characterEdited = await handleEditCharacter(character);
    if (characterEdited) {
      setCharacters([...characters.filter(c => c.id != character.id), characterEdited]);
    }
    getCharacters();
    return characterEdited;
  }

  async function removeCharacter(character: Character) {
    const status = await handleRemoveCharacter(character);
    if (status) {
      setCharacters([...characters.filter(c => c.id != character.id)]);
    }
    return status;
  }

  async function createTag(tag: Tag) {
    const tagCreated = await handleCreateTag(tag);
    if (tagCreated) {
      setTags([...tags, tagCreated]);
    }
    return tagCreated;
  }




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
        <AppContext.Provider value={{
          artists, setArtists,
          characters, setCharacters,
          tags, setTags,
          submissions, setSubmissions,
          createSubmission, createArtist, createCharacter, createTag,
          editArtist, editCharacter,
          removeArtist, removeCharacter
        }}>
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