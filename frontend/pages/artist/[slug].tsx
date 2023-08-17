import { useRouter } from "next/router";
import { useEffect, useState, useContext } from "react";
import {
  Avatar,
  Grid,
  Paper,
  Typography,
  Skeleton,
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
export default function Page() {
  const [artist, setArtist] = useState<Artist>();
  const [editShow, setEditShow] = useState(false);
  const [dialogOpen, setDialogOpen] = useState(false);
  const { data, setData } = useContext(DataContext);
  const router = useRouter();
  const toggleEdit = () => {
    console.log("w");
    setEditShow(!editShow);
  };
  const handleClickOpenDialog = () => {
    setDialogOpen(true);
  };
  const handleCloseDialog = () => {
    setDialogOpen(false);
  };

  async function removeArtist() {
    await axios.delete(`http://localhost:3001/artists/${artist?.id}`);

    // Remove artist from data
    const newData = { ...data };
    newData.artists = newData.artists.filter(
      (art: Artist) => art.id != artist?.id
    );
    setData(newData);

    router.push("/");
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
    <Paper>
      <Grid container spacing={2}>
        <Grid item lg={12}>
          <Skeleton variant="rectangular" width="100%" height={118} />
          Banner. Generado a partir de las submissions del artista. Si no
          placeholder/oculto
        </Grid>

        <Grid item lg={12}>
          {!editShow ? (
            <>
              <ArtistInfo
                artist={artist}
                toggleEdit={toggleEdit}
                handleClickOpenDialog={handleClickOpenDialog}
              />
            </>
          ) : (
            <>
              <ArtistEdit
                artist={artist}
                toggleEdit={toggleEdit}
                setArtist={setArtist}
              />
            </>
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
  );
}
