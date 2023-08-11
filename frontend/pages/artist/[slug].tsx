import { useRouter } from "next/router";
import { useEffect, useState, useContext } from "react";
import {
  Avatar,
  Grid,
  Paper,
  Typography,
  Skeleton,
} from "@mui/material";
import { DataContext } from "../_app";
import Artist from "../../interfaces/Artist";

export default function Page() {
  const [artist, setArtist] = useState<Artist>();
  const { data } = useContext(DataContext);

  const router = useRouter();

  useEffect(() => {
    const slug = router.query.slug;
    if (slug) {
      var id = parseInt(slug.toString());

      // Get submission
      data.artists.filter((artist: Artist) => {
        if (artist.id == id) {
          setArtist(artist);
        }
      });
    }
  }, [router.query.slug]);

  return (
    <Paper>
      <Grid container spacing={2}>
        <Grid item lg={12}>
          <Skeleton variant="rectangular" width="100%" height={118} />
          Banner. Generado a partir de las submissions del artista. Si no
          placeholder/oculto
        </Grid>
        <Grid item lg={12}>
          <Avatar sx={{ width: "6rem", height: "6rem" }} />
        </Grid>
        <Grid item lg={12}>
          <Typography variant="h4">{artist?.name}</Typography>
          {artist?.description}
        </Grid>
      </Grid>
    </Paper>
  );
}
