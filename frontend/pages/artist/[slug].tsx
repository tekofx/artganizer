import { useRouter } from "next/router";
import { useEffect, useState, useContext } from "react";
import {
  Grid,
  Paper,
  Typography,
  Button,
  Dialog,
  DialogActions,
  DialogTitle,
  Stack,
} from "@mui/material";
import { DataContext } from "../_app";
import Artist from "../../interfaces/Artist";
import Gallery from "../../components/Gallery";
import ArtistInfo from "../../components/Artist/ArtistInfo";
import ArtistEdit from "../../components/Artist/ArtistEdit";
import ClearIcon from "@mui/icons-material/Clear";
import DoneIcon from "@mui/icons-material/Done";
import axios from "axios";
import { emptyArtist } from "../../src/emptyEntities";
import Head from "next/head";
export default function Page() {
  const [artist, setArtist] = useState<Artist>(emptyArtist);
  const [editShow, setEditShow] = useState(false);
  const [dialogOpen, setDialogOpen] = useState(false);
  const { data, setData } = useContext(DataContext);
  const router = useRouter();
  const toggleEdit = () => {
    setEditShow(!editShow);
  };
  const handleClickOpenDialog = () => {
    setDialogOpen(true);
  };
  const handleCloseDialog = () => {
    setDialogOpen(false);
  };

  async function removeArtist() {
    await axios
      .delete(process.env.API_URL + `/artists/${artist?.id}`)
      .then(() => {
        // Remove artist from data
        const newData = { ...data };
        newData.artists = newData.artists.filter(
          (art: Artist) => art.id != artist?.id
        );
        setData(newData);
      })
      .catch((error) => {
        console.log(error);
      })
      .finally(() => {
        handleCloseDialog();
        router.push("/");
      });
  }

  useEffect(() => {
    const slug = router.query.slug;
    if (slug) {
      var id = parseInt(slug.toString());

      // Get artist from data
      data.artists.filter((artist: Artist) => {
        if (artist.id == id) {
          setArtist(artist);
        }
      });
    }
  }, [router.query.slug]);

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
            <Gallery artist={artist} />
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
                onClick={removeArtist}
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
