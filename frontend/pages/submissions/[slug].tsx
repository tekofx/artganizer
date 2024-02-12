import { Grid, Paper } from "@mui/material";
import axios from "axios";
import Layout from "components/Layout";
import cookie from "cookie";
import { GetServerSideProps, NextPage } from "next";
import Head from "next/head";
import { useEffect, useState } from "react";
import BottomPanel from "../../components/Layout/Panels/BottomPanel";
import RightPanel from "../../components/Layout/Panels/RightPanel/RightPanel";
import Submission from "../../interfaces/Submission";
import { useAppContext } from "../_app";

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
  const cookies = context.req.headers.cookie;
  if (!cookies) {
    return {
      redirect: {
        destination: '/login',
        permanent: false,
      },
    }
  }
  const token = cookie.parse(cookies).TOKEN;
  if (slug) {
    var id = parseInt(slug.toString());
    const res = await axios
      .get(`${baseUrl}/api/submissions/${id}`, {
        headers: {
          Authorization: "Bearer " + token,
        }
      })
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
  const { isMobile } = useAppContext();

  const [width, setWidth] = useState("auto");
  const [height, setHeight] = useState("auto");


  useEffect(() => {
    if (submission.height > submission.width) {
      // Vertical
      if (isMobile) {
        setWidth("100%")
        setHeight("auto")
      } else {

        setWidth("auto")
        setHeight("100%")
      }
    } else {
      // Horizontal
      setWidth("100%")
      setHeight("auto")
    }
  }, [submission]);

  return (
    <Layout>
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
            <Paper
              elevation={3}
              sx={{
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                height: { xs: "auto", lg: "85vh" }
              }}
            >
              <img
                src={submission?.image}
                style={{
                  width: width,
                  height: height
                }}
              />
            </Paper>
          </Grid>
          <Grid item xs={12} lg={3}>
            <RightPanel
              submission={pageSubmission}
              setSubmission={setPageSubmission}
            />

          </Grid>

          {isMobile && (
            <Grid item xs={12} >
              <br />
              <br />

            </Grid>
          )
          }

          {
            !isMobile && (

              <Grid item xs={12} >
                <BottomPanel current={submission} />
              </Grid>
            )}
        </Grid>
      </Paper>
    </Layout>
  );
};


export default Page;