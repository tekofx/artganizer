import { CacheProvider, EmotionCache } from "@emotion/react";
import { useMediaQuery } from "@mui/material";
import CssBaseline from "@mui/material/CssBaseline";
import { ThemeProvider } from "@mui/material/styles";
import { AppProps } from "next/app";
import Head from "next/head";
import {
  Dispatch,
  SetStateAction,
  createContext,
  useContext,
  useEffect,
  useState,
} from "react";
import Cookies from "universal-cookie";
import {
  Artist,
  Character,
  Filters,
  Settings,
  Submission,
  Tag,
} from "../interfaces";
import {
  handleCreateArtist,
  handleEditArtist,
  handleGetArtists,
  handleRemoveArtist,
} from "../src/api/artists";
import {
  handleCreateCharacter,
  handleEditCharacter,
  handleGetCharacters,
  handleRemoveCharacter,
} from "../src/api/characters";
import { handleEditSettings, handleGetSettings, handleResetSettings } from "../src/api/settings";
import {
  handleCreateSubmission,
  handleEditSubmission,
  handleGetSubmissions,
  handleRemoveSubmission,
} from "../src/api/submissions";
import {
  handleCreateTag,
  handleEditTag,
  handleGetTags,
  handleRemoveTag,
} from "../src/api/tags";
import createEmotionCache from "../src/createEmotionCache";
import { defaultSettings, emptyFilters } from "../src/emptyEntities";
import theme from "../src/theme";
import "../styles/styles.css";
var cookies = new Cookies();

// Client-side cache, shared for the whole session of the user in the browser.
const clientSideEmotionCache = createEmotionCache();

export interface MyAppProps extends AppProps {
  emotionCache?: EmotionCache;
}

interface AppContextType {
  isMobile: boolean;
  artists: Artist[];
  setArtists: Dispatch<SetStateAction<Artist[]>>;
  characters: Character[];
  setCharacters: Dispatch<SetStateAction<Character[]>>;
  tags: Tag[];
  setTags: Dispatch<SetStateAction<Tag[]>>;
  submissions: Submission[];
  setSubmissions: Dispatch<SetStateAction<Submission[]>>;
  settings: Settings;
  setSettings: Dispatch<SetStateAction<Settings>>;
  filters: Filters;
  setFilters: Dispatch<SetStateAction<Filters>>;
  // eslint-disable-next-line no-unused-vars
  createSubmission(submission: Submission): Promise<Submission>;
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
  // eslint-disable-next-line no-unused-vars
  editSubmission(submission: Submission): Promise<Submission | undefined>;
  // eslint-disable-next-line no-unused-vars
  removeSubmission(submission: Submission): Promise<boolean | undefined>;
  // eslint-disable-next-line no-unused-vars
  editTag(tag: Tag): Promise<Tag | undefined>;
  // eslint-disable-next-line no-unused-vars
  removeTag(tag: Tag): Promise<boolean | undefined>;
  // eslint-disable-next-line no-unused-vars
  editSettings(settings: Settings): Promise<Settings | undefined>;
  // eslint-disable-next-line no-unused-vars
  resetSettings(): Promise<Settings | undefined>;

  getAllAppData(): Promise<void>;
  getArtists(): Promise<void>;
  getCharacters(): Promise<void>;
  getTags(): Promise<void>;
  getSubmissions(): Promise<void>;
  getSettings(): Promise<void>;
}

const AppContext = createContext<AppContextType | null>(null);

export default function MyApp(props: MyAppProps) {
  const { Component, emotionCache = clientSideEmotionCache, pageProps } = props;
  const isMobile = useMediaQuery(theme.breakpoints.down("md"));
  const [artists, setArtists] = useState<Artist[]>([]);
  const [submissions, setSubmissions] = useState<Submission[]>([]);
  const [characters, setCharacters] = useState<Character[]>([]);
  const [tags, setTags] = useState<Tag[]>([]);
  const [settings, setSettings] = useState<Settings>(defaultSettings);
  const [filters, setFilters] = useState<Filters>(emptyFilters);


  async function getAllAppData() {
    await getArtists();
    await getSubmissions();
    await getCharacters();
    await getTags();
    await getSettings();
  }

  async function createSubmission(submission: Submission) {
    var submissionCreated: Submission;
    try {
      submissionCreated = await handleCreateSubmission(submission);
    } catch (e: any) {
      console.log("app.tsx: " + e.message);
      throw e;
    }
    setSubmissions([...submissions, submissionCreated]);
    getTags();
    return submissionCreated;
  }

  async function editSubmission(submission: Submission) {
    const submissionEdited = await handleEditSubmission(submission);
    if (submissionEdited) {
      setSubmissions([
        ...submissions.filter((s) => s.id != submission.id),
        submissionEdited,
      ]);
    }
    return submissionEdited;
  }

  async function removeSubmission(submission: Submission) {
    const status = await handleRemoveSubmission(submission);
    if (status) {
      setSubmissions([...submissions.filter((s) => s.id != submission.id)]);
    }
    return status;
  }

  async function getArtists() {
    try {
      const response = await handleGetArtists();
      setArtists(response);
    } catch (e: any) {
      console.log("app.tsx: " + e.message);
      throw e;
    }
  }

  async function createArtist(artist: Artist) {
    const artistCreated = await handleCreateArtist(artist);
    getArtists();
    return artistCreated;
  }

  async function editArtist(artist: Artist) {
    const artistEdited = await handleEditArtist(artist);
    if (artistEdited) {
      setArtists([...artists.filter((a) => a.id != artist.id), artistEdited]);
    }
    return artistEdited;
  }

  async function removeArtist(artist: Artist) {
    const status = await handleRemoveArtist(artist);
    if (status) {
      setArtists([...artists.filter((a) => a.id != artist.id)]);
    }
    return status;
  }

  async function getCharacters() {
    try {
      const response = await handleGetCharacters();
      setCharacters(response);
    } catch (e: any) {
      console.log("app.tsx: " + e.message);
      throw e;
    }
  }


  async function createCharacter(character: Character) {
    const characterCreated = await handleCreateCharacter(character);
    getCharacters();
    return characterCreated;
  }

  async function editCharacter(character: Character) {
    const characterEdited = await handleEditCharacter(character);
    if (characterEdited) {
      setCharacters([
        ...characters.filter((c) => c.id != character.id),
        characterEdited,
      ]);
    }
    getCharacters();
    return characterEdited;
  }

  async function removeCharacter(character: Character) {
    const status = await handleRemoveCharacter(character);
    if (status) {
      setCharacters([...characters.filter((c) => c.id != character.id)]);
    }
    return status;
  }

  async function getTags() {
    try {
      const response = await handleGetTags();
      setTags(response);
    } catch (e: any) {
      console.log("app.tsx: " + e.message);
      throw e;
    }
  }

  async function createTag(tag: Tag) {
    const tagCreated = await handleCreateTag(tag);
    if (tagCreated) {
      setTags([...tags, tagCreated]);
    }
    return tagCreated;
  }

  async function editTag(tag: Tag) {
    const tagEdited = await handleEditTag(tag);
    if (tagEdited) {
      setTags([...tags.filter((t) => t.id != tag.id), tagEdited]);
    }
    return tagEdited;
  }
  async function removeTag(tag: Tag) {
    const status = await handleRemoveTag(tag);
    if (status) {
      setTags([...tags.filter((t) => t.id != tag.id)]);
    }
    return status;
  }
  async function getSettings() {
    try {
      const response = await handleGetSettings();
      setSettings(response || defaultSettings);
    } catch (e: any) {
      console.log("app.tsx: " + e.message);
      throw e;
    }
  }
  async function editSettings(settings: Settings) {
    const status = await handleEditSettings(settings);
    if (status) {
      setSettings(status);
    }
    return status;
  }

  async function resetSettings() {
    const status = await handleResetSettings();
    if (status) {
      setSettings(status);
    }
    return status;
  }

  async function getSubmissions() {
    try {
      const response = await handleGetSubmissions();
      setSubmissions(response);
    } catch (e: any) {
      console.log("app.tsx: " + e.message);
      throw e;
    }
  }

  useEffect(() => {
    console.log(cookies.get("TOKEN"));
    if (cookies.get("TOKEN") == undefined) {
      return;
    }
    getArtists();
    getSubmissions();
    getCharacters();
    getTags();
    getSettings();
  }, []);

  return (
    <CacheProvider value={emotionCache}>
      <Head>
        <meta name="viewport" content="initial-scale=1, width=device-width" />
      </Head>
      <ThemeProvider theme={theme}>
        {/* CssBaseline kickstart an elegant, consistent, and simple baseline to build upon. */}
        <CssBaseline />
        <AppContext.Provider
          value={{
            artists,
            setArtists,
            characters,
            setCharacters,
            tags,
            setTags,
            submissions,
            setSubmissions,
            settings,
            setSettings,
            filters,
            setFilters,
            createSubmission,
            createArtist,
            createCharacter,
            createTag,
            editArtist,
            editCharacter,
            editSubmission,
            editTag,
            removeArtist,
            removeCharacter,
            removeSubmission,
            removeTag,
            editSettings,
            resetSettings,
            isMobile,
            getArtists,
            getCharacters,
            getTags,
            getSubmissions,
            getSettings,
            getAllAppData,

          }}
        >
          <Component {...pageProps} />
        </AppContext.Provider>
      </ThemeProvider>
    </CacheProvider>
  );
}

export function useAppContext() {
  const context = useContext(AppContext);
  if (!context) {
    throw new Error("useAppContext must be used within an AppProvider");
  }
  return context;
}
