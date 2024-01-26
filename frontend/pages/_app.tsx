import { CacheProvider, EmotionCache } from "@emotion/react";
import { Grid } from "@mui/material";
import CssBaseline from "@mui/material/CssBaseline";
import { ThemeProvider } from "@mui/material/styles";
import { AppProps } from "next/app";
import Head from "next/head";
import LateralPanel from "../components/Panels/LeftPanel";
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



export default function MyApp(props: MyAppProps) {
  const { Component, emotionCache = clientSideEmotionCache, pageProps } = props;

  return (
    <CacheProvider value={emotionCache}>
      <Head>
        <meta name="viewport" content="initial-scale=1, width=device-width" />
      </Head>
      <ThemeProvider theme={theme}>
        {/* CssBaseline kickstart an elegant, consistent, and simple baseline to build upon. */}
        <CssBaseline />
        <Grid container>
          <Grid item lg={2} position="fixed">
            <LateralPanel />
          </Grid>
          <Grid item lg={10} style={{ marginLeft: "16.66%" }}>
            <Component {...pageProps} />
          </Grid>
        </Grid>
      </ThemeProvider>
    </CacheProvider>
  );
}
