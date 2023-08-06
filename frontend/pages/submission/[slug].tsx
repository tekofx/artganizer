import { useRouter } from "next/router";
import { useEffect, useState, useContext } from "react";
import { Grid, Paper, Typography, Stack, MenuItem, Icon } from "@mui/material";
import LocalOfferIcon from "@mui/icons-material/LocalOffer";
import Folder from "../../interfaces/Folder";
import Submission from "../../interfaces/Submission";
import Tag from "../../interfaces/Tag";
import { DataContext } from "../_app";
import { TempleBuddhist } from "@mui/icons-material";
import TagList from "../../components/TagList";
import CharacterList from "../../components/CharacterList";
import ArtistList from "../../components/ArtistList";

export default function Page() {
  const [submission, setSubmission] = useState<Submission>();
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
          // If tag not in filters add it
        }
      });
    }
  }, [router.query.slug]);

  return (
    <Paper>
      <Grid container>
        <Grid item lg={8}>
          <img src={submission?.image} width="100%" />
          <Typography>{submission?.title}</Typography>
          <Typography>{submission?.description}</Typography>
        </Grid>
        <Grid item>
          <Typography>Tags</Typography>
          <TagList tags={submission?.tags} />
          <Typography>Characters</Typography>
          <CharacterList characters={submission?.characters} />
          <Typography>Artist</Typography>
          <ArtistList artists={submission?.artists} />
        </Grid>
      </Grid>

      {router.query.slug}
    </Paper>
  );
}
