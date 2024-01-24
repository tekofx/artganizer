import { useRouter } from "next/router";
import { useEffect, useState } from "react";
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
import Artist from "../../interfaces/Artist";
import Gallery from "../../components/Gallery";
import ArtistInfo from "../../components/Artist/ArtistInfo";
import ArtistEdit from "../../components/Artist/ArtistEdit";
import ClearIcon from "@mui/icons-material/Clear";
import DoneIcon from "@mui/icons-material/Done";
import axios from "axios";
import { emptyArtist, emptyFilters } from "../../src/emptyEntities";
import Head from "next/head";
export default function Page() {
  const [artist, setArtist] = useState<Artist>(emptyArtist);
  const [editShow, setEditShow] = useState(false);
  const [dialogOpen, setDialogOpen] = useState(false);
  const router = useRouter();

  useEffect(() => {
    const getArtist = async (id: number) => {
      var res = await axios.get(process.env.API_URL + `/artists/${id}`);
      setArtist(res.data);
      console.log(res.data);
    };

    const slug = router.query.slug;
    if (slug) {
      var id = parseInt(slug.toString());
      // Get artist
      getArtist(id);
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

  async function removeArtist() {
    await axios
      .delete(process.env.API_URL + `/artists/${artist?.id}`)
      .then(() => {
      })
      .catch((error) => {
        console.log(error);
      })
      .finally(() => {
        handleCloseDialog();
        router.push("/");
      });
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
