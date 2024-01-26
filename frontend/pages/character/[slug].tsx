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
import CharacterEdit from "../../components/Character/CharacterEdit";
import CharacterInfo from "../../components/Character/CharacterInfo";
import Gallery from "../../components/Gallery";
import Character from "../../interfaces/Character";
import { emptyCharacter, emptyFilters } from "../../src/emptyEntities";
import { useAppContext } from "../_app";
export default function Page() {
  const { characters, removeCharacter } = useAppContext();
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

  async function onYesClick() {
    await removeCharacter(character);
    handleCloseDialog();
    router.push("/");
  }

  useEffect(() => {

    const slug = router.query.slug;
    if (slug) {
      var id = parseInt(slug.toString());
      // Get character
      const character = characters.find((character) => character.id === id);
      if (character) {
        setCharacter(character);
      }
    }
  }, [router.query.slug]);

  return (
    <>
      <Head>
        <title>Artganizer</title>
      </Head>
      <Paper>
        <Grid container spacing={2} >
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
            <Gallery filters={emptyFilters} character={character} />
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
