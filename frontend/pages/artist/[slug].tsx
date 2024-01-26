import ClearIcon from "@mui/icons-material/Clear";
import DoneIcon from "@mui/icons-material/Done";
import {
  Button,
  Dialog,
  DialogActions,
  DialogTitle,
  Grid,
  Paper,
  Stack,
  Typography,
} from "@mui/material";
import Head from "next/head";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import ArtistEdit from "../../components/Artist/ArtistEdit";
import ArtistInfo from "../../components/Artist/ArtistInfo";
import Gallery from "../../components/Gallery";
import Artist from "../../interfaces/Artist";
import { emptyArtist, emptyFilters } from "../../src/emptyEntities";
import { useAppContext } from "../_app";
export default function Page() {
  const { artists, removeArtist } = useAppContext();
  const [artist, setArtist] = useState<Artist>(emptyArtist);
  const [editShow, setEditShow] = useState(false);
  const [dialogOpen, setDialogOpen] = useState(false);
  const router = useRouter();

  useEffect(() => {
    const slug = router.query.slug;
    if (slug) {
      var id = parseInt(slug.toString());
      // Get artist
      const artist = artists.find((artist) => artist.id === id);
      if (artist) {
        setArtist(artist);
      }
    }
  }
    , [router.query.slug]);
  const toggleEdit = () => {
    setEditShow(!editShow);
  };
  const handleClickOpenDialog = () => {
    setDialogOpen(true);
  };
  const handleCloseDialog = () => {
    setDialogOpen(false);
  };

  async function onYesClick() {
    await removeArtist(artist);
    handleCloseDialog();
    router.push("/");
  }

  return (
    <>
      <Head>
        <title>{artist.name} | Artganizer</title>
      </Head>
      <Paper>
        <Grid container spacing={2}>
          <Grid item lg={12}>
            {!editShow ? (
              <ArtistInfo
                artist={artist}
                toggleEdit={toggleEdit}
                handleClickOpenDialog={handleClickOpenDialog}
              />
            ) : (
              <ArtistEdit
                artist={artist}
                toggleEdit={toggleEdit}
                setArtist={setArtist}
              />
            )}
          </Grid>
          <Grid item lg={12}>
            <Gallery filters={emptyFilters} />
          </Grid>
        </Grid>
        <Dialog open={dialogOpen}>
          <DialogTitle>
            <Typography>
              Are you sure you want to remove this submission?
            </Typography>
          </DialogTitle>
          <DialogActions>
            <Stack direction="row" width="100%" spacing={2}>
              <Button
                variant="contained"
                size="small"
                startIcon={<DoneIcon />}
                onClick={onYesClick}
              >
                Yes
              </Button>
              <Button
                variant="contained"
                size="small"
                startIcon={<ClearIcon />}
                onClick={handleCloseDialog}
              >
                No
              </Button>
            </Stack>
          </DialogActions>
        </Dialog>
      </Paper>
    </>
  );
}
