import { useRouter } from "next/router";
import { useEffect, useState, useContext } from "react";
import { Grid, Paper } from "@mui/material";
import RightPanel from "../../components/RightPanel/RightPanel";
import Submission from "../../interfaces/Submission";
import { DataContext } from "../_app";

export default function Page() {
  const [submission, setSubmission] = useState<Submission>();
  const { data } = useContext(DataContext);

  const router = useRouter();

  useEffect(() => {
    const slug = router.query.slug;
    if (slug) {
      var id = parseInt(slug.toString());
      console.log(data.submissions);
      // Get submission
      data.submissions.filter((sub: Submission) => {
        if (sub.id == id) {
          setSubmission(sub);
          console.log(sub);
        }
      });
    }
  }, [router.query.slug]);

  return (
    <Paper>
      <Grid container spacing={2}>
        <Grid item lg={9}>
          <img src={submission?.image} width="100%" />
        </Grid>
        <Grid item lg={3}>
          <RightPanel submission={submission} />
        </Grid>
      </Grid>
    </Paper>
  );
}
