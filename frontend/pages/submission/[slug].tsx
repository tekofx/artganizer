import { Grid, Paper } from "@mui/material";
import axios from "axios";
import { GetServerSideProps, NextPage } from "next";
import Head from "next/head";
import { useState } from "react";
import BottomPanel from "../../components/Panels/BottomPanel";
import RightPanel from "../../components/Panels/RightPanel/RightPanel";
import Submission from "../../interfaces/Submission";

interface PageProps {
  submission: Submission;
}

export const getServerSideProps: GetServerSideProps<PageProps> = async (
  context
) => {
  const slug = context.params?.slug;
  if (slug) {
    var id = parseInt(slug.toString());
    const res = await axios
      .get(process.env.API_URL + "/submissions/" + id)
      .catch(() => {
        return undefined;
      });
    if (res == undefined) return { notFound: true };

    var submission: Submission = res.data;

    return { props: { submission } };
  }

  return { notFound: true };
};
const Page: NextPage<PageProps> = ({ submission }) => {
  const [pageSubmission, setPageSubmission] = useState<Submission>(submission);

  return (
    <>
      <Head>
        <title>{submission?.title}</title>
      </Head>
      <Paper
        sx={{
          maxHeight: "100vh",
        }}
      >
        <Grid container>
          <Grid item lg={9}>
            <Grid container>
              <Grid item xs={12}>
                <Paper
                  elevation={3}
                  sx={{
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                    height: "80vh",
                  }}
                >
                  <img
                    src={submission?.image}
                    width="auto"
                    height="100%"
                    style={{ maxHeight: "100vh", maxWidth: "100%" }}
                  />
                </Paper>
              </Grid>
              <Grid item xs={12}>
                <BottomPanel current={submission} />
              </Grid>
            </Grid>
          </Grid>
          <Grid item lg={3}>
            <RightPanel
              submission={pageSubmission}
              setSubmission={setPageSubmission}
            />
          </Grid>
        </Grid>
      </Paper>
    </>
  );
};
export default Page;
