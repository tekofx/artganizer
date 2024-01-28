import { Button, Grid, Typography } from "@mui/material";
import Head from "next/head";
import { useRouter } from "next/router";

export default function NotFound() {
  const router = useRouter();

  function onClick() {
    router.push("/");
  }
  return (
    <>
      <Head>
        <title>404 | Not found</title>
      </Head>
      <Grid
        container
        direction="column"
        justifyContent="center"
        alignItems="center"
        style={{ minHeight: "100vh" }}
      >
        <Grid item></Grid>
        <Typography variant="h3">404 Not Found</Typography>
        <Button variant="contained" onClick={onClick}>
          Go back to home
        </Button>
      </Grid>
    </>
  );
}
