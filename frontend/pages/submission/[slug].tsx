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
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
} from "@mui/material";
import LocalOfferIcon from "@mui/icons-material/LocalOffer";
import Folder from "../../interfaces/Folder";
import Submission from "../../interfaces/Submission";
import Tag from "../../interfaces/Tag";
import { DataContext } from "../_app";
import { TempleBuddhist } from "@mui/icons-material";
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
          <Button onClick={() => setOpen(true)}>Edit</Button>
          <Dialog open={open} fullScreen>
            <SubmissionForm
              submission={submission}
              setSubmission={setSubmission}
              setOpen={setOpen}
              open={open}
            />
          </Dialog>
          <Typography>{submission?.title}</Typography>
          <Typography>{submission?.description}</Typography>
          <Rating value={submission?.rating} readOnly />
          <Typography>{submission?.date.toString()}</Typography>
          <Typography>
            {submission?.width}x{submission?.height}
          </Typography>
          <Typography>{submission?.format.toUpperCase()}</Typography>
          <Typography>Tags</Typography>
          <TagList tags={submission?.tags} />
          <Typography>Characters</Typography>
          <CharacterList characters={submission?.characters} />
          <Typography>Artist</Typography>
          <ArtistList artists={submission?.artists} />
        </Grid>
      </Grid>
    </Paper>
  );
}
