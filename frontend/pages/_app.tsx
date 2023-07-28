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
}


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
  const [data, setData] = useState<{ labels: Label[], folders: Folder[], submissions: Submission[] }>({ labels: [], folders: [], submissions: [] });

  useEffect(() => {
    axios.get("http://localhost:3001/labels", {
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Content-Type': 'application/json',
      }
    }).then((response) => {
      data.labels = response.data;
      setData(data)
    }).catch((error) => {
      console.log(error);
    });

    axios.get("http://localhost:3001/folders", {
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Content-Type': 'application/json',
      }
    }).then((response) => {
      data.folders = response.data;
      setData(data)
    }).catch((error) => {
      console.log(error);
    });

    axios.get("http://localhost:3001/submissions", {
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Content-Type': 'application/json',
      }
    }).then((response) => {
      data.submissions = response.data;
      setData(data)
    }).catch((error) => {
      console.log(error);
    });


  }, []);

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
