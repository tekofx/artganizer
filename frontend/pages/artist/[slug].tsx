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
import axios from "axios";
import { GetServerSideProps, NextPage } from "next";
import Head from "next/head";
import { useRouter } from "next/router";
import { useState } from "react";
import ArtistEdit from "../../components/Artist/ArtistEdit";
import ArtistInfo from "../../components/Artist/ArtistInfo";
import Gallery from "../../components/Gallery";
import Artist from "../../interfaces/Artist";
import { useAppContext } from "../_app";

interface PageProps {
  artist: Artist;
}

export const getServerSideProps: GetServerSideProps<PageProps> = async (
  context
) => {
  const slug = context.params?.slug;
  if (slug) {
    var id = parseInt(slug.toString());
    const res = await axios
      .get(process.env.API_URL + "/artists/" + id)
      .catch(() => {
        return undefined;
      });
    if (res == undefined) return { notFound: true };

    var artist: Artist = res.data;

    return { props: { artist } };
  }

  return { notFound: true };
};

const Page: NextPage<PageProps> = ({ artist }) => {
  const { removeArtist } = useAppContext();
  const [pageArtist, setPageArtist] = useState<Artist>(artist);
  const [editShow, setEditShow] = useState(false);
  const [dialogOpen, setDialogOpen] = useState(false);
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
                artist={pageArtist}
                toggleEdit={toggleEdit}
                handleClickOpenDialog={handleClickOpenDialog}
              />
            ) : (
              <ArtistEdit
                artist={pageArtist}
                toggleEdit={toggleEdit}
                setArtist={setPageArtist}
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
};
export default Page;
