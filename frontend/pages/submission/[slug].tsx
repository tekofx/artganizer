import { useRouter } from "next/router";
import { useEffect, useState, useContext } from "react";
import { Grid, Paper } from "@mui/material";
import RightPanel from "../../components/RightPanel/RightPanel";
import Submission from "../../interfaces/Submission";
import { DataContext } from "../_app";
import { emptySubmission } from "../../src/utils";
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
          console.log(sub);
        }
      });
    }
  }, [router.query.slug]);

  return (
    <Paper>
      <Grid container>
        <Grid item lg={9}>
          <img src={submission?.image} width="100%" />
        </Grid>
        <Grid item lg={3}>
          <RightPanel submission={submission} setSubmission={setSubmission} />
        </Grid>
      </Grid>
    </Paper>
  );
}
