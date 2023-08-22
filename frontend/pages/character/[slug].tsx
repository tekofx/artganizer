import { useRouter } from "next/router";
import { useEffect, useState, useContext } from "react";
import {
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
import Gallery from "../../components/Gallery";
import ClearIcon from "@mui/icons-material/Clear";
import DoneIcon from "@mui/icons-material/Done";
import axios from "axios";
import { emptyCharacter } from "../../src/utils";
import Character from "../../interfaces/Character";
import CharacterInfo from "../../components/Character/CharacterInfo";
import CharacterEdit from "../../components/Character/CharacterEdit";
export default function Page() {
  const [character, setCharacter] = useState<Character>(emptyCharacter);
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

  async function removeCharacter() {
    await axios.delete(`http://localhost:3001/characters/${character?.id}`);
    handleCloseDialog();
    // Remove character from data
    const newData = { ...data };
    newData.characters = newData.characters.filter(
      (char: Character) => char.id != character?.id
    );
    setData(newData);

    router.push("/");
  }

  useEffect(() => {
    const slug = router.query.slug;
    if (slug) {
      var id = parseInt(slug.toString());

      // Get character from data
      data.characters.filter((character: Character) => {
        if (character.id == id) {
          console.log(character);
          setCharacter(character);
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
              <CharacterInfo
                character={character}
                toggleEdit={toggleEdit}
                handleClickOpenDialog={handleClickOpenDialog}
              />
            </>
          ) : (
            <>
              <CharacterEdit
                character={character}
                toggleEdit={toggleEdit}
                setCharacter={setCharacter}
              />
            </>
          )}
        </Grid>
        <Grid item lg={12}>
          <Gallery character={character} />
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
  );
}
