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
import Gallery from "../../components/Gallery";
import ClearIcon from "@mui/icons-material/Clear";
import DoneIcon from "@mui/icons-material/Done";
import axios from "axios";
import { emptyCharacter, emptyFilters } from "../../src/emptyEntities";
import Character from "../../interfaces/Character";
import CharacterInfo from "../../components/Character/CharacterInfo";
import CharacterEdit from "../../components/Character/CharacterEdit";
import Head from "next/head";
export default function Page() {
  const [character, setCharacter] = useState<Character>(emptyCharacter);
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

  async function removeCharacter() {
    await axios
      .delete(process.env.API_URL + `/characters/${character?.id}`)
      .then(() => {
        // Remove character from data
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
    const getCharacter = async (id: number) => {
      var res = await axios.get(process.env.API_URL + `/characters/${id}`);
      setCharacter(res.data);
      console.log(res.data);
    }
    const slug = router.query.slug;
    if (slug) {
      var id = parseInt(slug.toString());
      getCharacter(id);
    }
  }, [router.query.slug]);

  return (
    <>
      <Head>
        <title>Artganizer</title>
      </Head>
      <Paper>
        <Grid container spacing={2} sx={{ paddingLeft: 2 }}>
          <Grid item lg={12}>
            {!editShow ? (
              <CharacterInfo
                character={character}
                toggleEdit={toggleEdit}
                handleClickOpenDialog={handleClickOpenDialog}
              />
            ) : (
              <CharacterEdit
                character={character}
                toggleEdit={toggleEdit}
                setCharacter={setCharacter}
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
                onClick={removeCharacter}
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
