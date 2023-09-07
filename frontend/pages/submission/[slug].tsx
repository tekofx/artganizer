import { useRouter } from "next/router";
import { useEffect, useState, useContext } from "react";
import { Grid, Paper } from "@mui/material";
import RightPanel from "../../components/Panels/RightPanel/RightPanel";
import Submission from "../../interfaces/Submission";
import { DataContext } from "../_app";
import { emptySubmission } from "../../src/emptyEntities";
import Head from "next/head";
export default function Page() {
  const [submission, setSubmission] = useState<Submission>(emptySubmission);
  const { data } = useContext(DataContext);

  const router = useRouter();

  useEffect(() => {
    const slug = router.query.slug;
    if (slug) {
      var id = parseInt(slug.toString());
      // Get submission
      data.submissions.filter((sub: Submission) => {
        if (sub.id == id) {
          setSubmission(sub);
        }
      });
    }
  }, [router.query.slug]);

  return (
    <>
      <Head>
        <title>{submission?.title}</title>
      </Head>
      <Paper>
        <Grid container>
          <Grid item lg={9}>
            <Paper
              elevation={3}
              sx={{
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                height: "100vh",
              }}
            >
              <img
                src={submission?.image}
                width="auto"
                style={{ maxHeight: "100vh", maxWidth: "100%" }}
              />
            </Paper>
          </Grid>
          <Grid item lg={3}>
            <RightPanel submission={submission} setSubmission={setSubmission} />
          </Grid>
        </Grid>
      </Paper>
    </>
  );
}
