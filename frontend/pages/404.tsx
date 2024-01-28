import { Button, Typography } from "@mui/material";
import Head from "next/head";
import { useRouter } from "next/router";

interface Props {
  title?: string;
}

export default function NotFound({ title }: Props) {
  const router = useRouter();

  function onClick() {
    router.push("/");
  }
  return (
    <>
      <Head>
        <title>404 | Not found</title>
      </Head>
      {title}
      <Typography variant="h3">404 Not Found</Typography>
      <Button variant="contained" onClick={onClick}>
        Go back to home
      </Button>
    </>
  );
}
