import { useEffect, createContext, useState } from 'react';
import Head from 'next/head';
import { AppProps } from 'next/app';
import { ThemeProvider } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import { CacheProvider, EmotionCache } from '@emotion/react';
import theme from '../src/theme';
import createEmotionCache from '../src/createEmotionCache';
import NavBar from '../components/NavBar';
import { Grid } from '@mui/material';
import LateralPanel from '../components/LateralPanel';
import Label from '../interfaces/Label';
import "../styles/styles.css";
import axios from 'axios';
import Submission from '../interfaces/Submission';
import { Folder } from '../interfaces/Folder';
// Client-side cache, shared for the whole session of the user in the browser.
const clientSideEmotionCache = createEmotionCache();

export interface MyAppProps extends AppProps {
  emotionCache?: EmotionCache;
  labels: Label[];
  folders: Folder[];
  submissions: Submission[];
}


// Inital props
MyApp.getInitialProps = async () => {
  const labelsResponse = await axios.get<Label[]>("http://localhost:3001/labels", {
    headers: {
      "Access-Control-Allow-Origin": "*",
      "Content-Type": "application/json"
    }
  });
  const labels = labelsResponse.data;

  const foldersResponse = await axios.get<Folder[]>("http://localhost:3001/folders", {
    headers: {
      "Access-Control-Allow-Origin": "*",
      "Content-Type": "application/json"
    }
  });
  const folders = foldersResponse.data;

  const submissionsResponse = await axios.get<Submission[]>("http://localhost:3001/submissions", {
    headers: {
      "Access-Control-Allow-Origin": "*",
      "Content-Type": "application/json"
    }
  });
  const submissions = submissionsResponse.data;

  return { labels, folders, submissions };
};


interface DataContextType {
  data: {
    labels: Label[];
    folders: Folder[];
    submissions: Submission[];
  };
  setData: (data: DataContextType["data"]) => void;
}

export const DataContext = createContext<DataContextType>({
  data: { labels: [], folders: [], submissions: [] },
  setData: () => { }
});


export default function MyApp(props: MyAppProps) {
  const { Component, emotionCache = clientSideEmotionCache, pageProps } = props;
  const [data, setData] = useState<{ labels: Label[]; folders: Folder[]; submissions: Submission[] }>({
    labels: props.labels,
    folders: props.folders,
    submissions: props.submissions
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
