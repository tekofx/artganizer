import { Grid, Paper } from "@mui/material";
import axios from "axios";
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

type ImageType = "vertical" | "horizontal";

export const getServerSideProps: GetServerSideProps<PageProps> = async (
  context
) => {
  const slug = context.params?.slug;
  const { req } = context;
  const protocol = req.headers["x-forwarded-proto"] || "http";
  const baseUrl = req ? `${protocol}://${req.headers.host}` : "";
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
  const [imageStyle, setImageStyle] = useState({ style: { height: 'auto', width: '100%' }, paperHeightXS: "100%" });
  const [imageType, setImageType] = useState<ImageType>("horizontal");
  const { isMobile } = useAppContext();

  useEffect(() => {
    setPageSubmission(submission);
    const img = new Image();
    img.src = submission?.image;
    img.onload = () => {
      if (img.width < img.height) {
        setImageType("vertical");
      } else {
        setImageType("horizontal");
      }
    };
  }
    , [submission]);


  useEffect(() => {
    if (imageType == "vertical") {
      if (isMobile) {
        setImageStyle({ style: { width: '100%', height: 'auto' }, paperHeightXS: "85vh" });
        return;
      }
      setImageStyle({ style: { width: 'auto', height: '100%' }, paperHeightXS: "100%" });
    } else {
      if (isMobile) {
        setImageStyle({ style: { height: 'auto', width: '100%' }, paperHeightXS: "85vh" });
        return;
      }
      setImageStyle({ style: { height: 'auto', width: '100%' }, paperHeightXS: "100%" });

    }
  }
    , [imageType]);

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
            <Paper
              elevation={3}
              sx={{
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                height: { xs: imageStyle.paperHeightXS, lg: "85vh" }
              }}
            >
              <img
                src={submission?.image}
                style={imageStyle.style}
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
    </>
  );
};


export default Page;