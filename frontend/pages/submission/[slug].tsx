import { useRouter } from "next/router";
import { useEffect, useState, useContext } from "react";
import {
  Grid,
  Paper,
  Typography,
  Stack,
  MenuItem,
  Rating,
  Icon,
  Button,
  Chip,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
} from "@mui/material";
import RightPanel from "../../components/RightPanel/RightPanel";
import LocalOfferIcon from "@mui/icons-material/LocalOffer";
import Folder from "../../interfaces/Folder";
import Submission from "../../interfaces/Submission";
import Tag from "../../interfaces/Tag";
import { DataContext } from "../_app";
import { TempleBuddhist } from "@mui/icons-material";
import ColorPalette from "../../components/ColorPalette";
import SubmissionForm from "../../components/SubmissionForm";

import TagList from "../../components/TagList";
import CharacterList from "../../components/CharacterList";
import ArtistList from "../../components/ArtistList";

export default function Page() {
  const [submission, setSubmission] = useState<Submission>();
  const [open, setOpen] = useState<boolean>(false);
  const { data, setData } = useContext(DataContext);

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
