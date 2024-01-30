import { Grid, Paper } from "@mui/material";
import axios from "axios";
import { GetServerSideProps, NextPage } from "next";
import Head from "next/head";
import { useState } from "react";
import BottomPanel from "../../components/Layout/Panels/BottomPanel";
import RightPanel from "../../components/Layout/Panels/RightPanel/RightPanel";
import Submission from "../../interfaces/Submission";

interface PageProps {
  submission: Submission;
}

export const getServerSideProps: GetServerSideProps<PageProps> = async (
  context
) => {
  const slug = context.params?.slug;
  const { req } = context;
  const protocol = req.headers["x-forwarded-proto"] || "http";
  const baseUrl = req ? `${protocol}://${req.headers.host}` : "";
  console.log(baseUrl);
  if (slug) {
    var id = parseInt(slug.toString());
    const res = await axios
      .get(baseUrl + "/api/submissions/" + id)
      .then((response) => {
        return response.data;
      })
      .catch(() => {
        return undefined;
      });
    if (res == undefined) return { notFound: true };

    var submission: Submission = res;

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
          height: "100%",
        }}
      >
        <Grid container spacing={{ xs: 2, lg: 0 }}>
          <Grid item xs={12} lg={9}>
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
              <Grid item xs={12} sx={{ display: { xs: "none", lg: "block" } }}>
                <BottomPanel current={submission} />
              </Grid>
            </Grid>
          </Grid>
          <Grid item xs={12} lg={3}>
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
