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
import CharacterEdit from "../../components/Character/CharacterEdit";
import CharacterInfo from "../../components/Character/CharacterInfo";
import Gallery from "../../components/Gallery";
import Character from "../../interfaces/Character";
import { useAppContext } from "../_app";

interface PageProps {
  character: Character;
}

export const getServerSideProps: GetServerSideProps<PageProps> = async (
  context
) => {
  const slug = context.params?.slug;
  if (slug) {
    var id = parseInt(slug.toString());
    const res = await axios
      .get("http://localhost:3000/api/characters/" + id)
      .catch(() => {
        return undefined;
      });
    if (res == undefined) return { notFound: true };

    var character: Character = res.data;

    return { props: { character } };
  }

  // Si no hay slug, devuelve notFound: true para mostrar la página 404
  return { notFound: true };
};

const Page: NextPage<PageProps> = ({ character }) => {
  const { removeCharacter } = useAppContext();
  const [pageCharacter, setPageCharacter] = useState<Character>(character);
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
    await removeCharacter(character);
    handleCloseDialog();
    router.push("/");
  }

  return (
    <>
      <Head>
        <title>Artganizer</title>
      </Head>
      <Paper>
        <Grid container spacing={2}>
          <Grid item lg={12}>
            {!editShow ? (
              <CharacterInfo
                character={pageCharacter}
                toggleEdit={toggleEdit}
                handleClickOpenDialog={handleClickOpenDialog}
              />
            ) : (
              <CharacterEdit
                character={pageCharacter}
                toggleEdit={toggleEdit}
                setCharacter={setPageCharacter}
              />
            )}
          </Grid>
          <Grid item lg={12}>
            <Gallery character={pageCharacter} />
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
