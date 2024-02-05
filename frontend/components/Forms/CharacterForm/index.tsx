import {
  Avatar,
  Box,
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Grid,
  IconButton,
  Stack
} from "@mui/material";
import { useState } from "react";
import { CharacterData } from "../../../../common/entitiesData";
import { AlertMessage } from "../../../interfaces";
import Character from "../../../interfaces/Character";
import { useAppContext } from "../../../pages/_app";
import { emptyCharacter } from "../../../src/emptyEntities";
import LimitedTextField from "../../LimitedTextField";
import Snack from "../../Snack";
import ProgressButton from "../ProgressButon";
interface Props {
  open: boolean;
  setOpen: React.Dispatch<React.SetStateAction<boolean>>;
  name?: string;
}

export default function CharacterForm({ open, setOpen, name }: Props) {
  const [character, setCharacter] = useState<Character>(
    name ? { ...emptyCharacter, name: name } : { ...emptyCharacter }
  );
  const { createCharacter, isMobile } = useAppContext();
  const [image, setImage] = useState<string>("/placeholder.jpg");
  const [loading, setLoading] = useState<boolean>(false);
  const [openSnack, setOpenSnack] = useState<boolean>(false);
  const [alertMessage, setAlertMessage] = useState<AlertMessage>({
    message: "Submission created",
    severity: "success",
  });

  function resetForm() {
    setImage("/placeholder.jpg");
    setCharacter(emptyCharacter);
  }

  function onImageUpload(event: any) {
    const newCharacter = { ...character };
    newCharacter.image = event.target.files[0];
    setCharacter(newCharacter);
    setImage(URL.createObjectURL(event.target.files[0]));
  }

  async function postCharacter() {
    setLoading(true);
    var result = await createCharacter(character);
    if (result) {
      setAlertMessage({
        message: "Character created",
        severity: "success",
      });
    } else {
      setAlertMessage({
        message: "Error: Character not created",
        severity: "error",
      });
    }

    setLoading(false);
    setOpen(false);
    setOpenSnack(true);
    resetForm();
  }

  function onCancel() {
    setOpen(false);
    resetForm();
  }

  return (
    <>
      <Dialog open={open} onClose={() => setOpen(false)} fullScreen={isMobile ? true : false}>
        <DialogTitle>Create Character</DialogTitle>
        <DialogContent sx={{ p: 2 }}>
          <Grid container spacing={4}>
            <Grid item xs={12} lg={4}>
              <input
                accept="image/png, image/jpeg"
                id="character-form"
                multiple
                type="file"
                hidden
                onChange={onImageUpload}
              />
              <Box display="flex" justifyContent="center" alignItems="center">
                <label htmlFor="artist-form-image">
                  <IconButton component="span">
                    <Avatar src={image} sx={{ width: "20vh", height: "20vh" }} />
                  </IconButton>
                </label>
              </Box>
            </Grid>
            <Grid item xs={12} lg={8}>
              <Stack spacing={2} sx={{ paddingTop: 1 }}>
                <LimitedTextField
                  label="Name"
                  maxLength={CharacterData.nameLenght}
                  multiline={false}
                  value={character.name}
                  onChange={(event) => {
                    setCharacter((prevCharacter) => ({
                      ...prevCharacter,
                      name: event.target.value,
                    }));
                  }}
                />
                <LimitedTextField
                  label="Description"
                  maxLength={CharacterData.descriptionLenght}
                  multiline
                  value={character.description}
                  onChange={(event: React.ChangeEvent<HTMLInputElement>) => {
                    setCharacter((prevCharacter) => ({
                      ...prevCharacter,
                      description: event.target.value,
                    }));
                  }}
                />
              </Stack>
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <Button disabled={loading} onClick={onCancel}>
            Cancel
          </Button>
          <ProgressButton
            loading={loading}
            disabled={character.name == ""}
            onClick={postCharacter}
            text="Create"
          />
        </DialogActions>
      </Dialog>
      <Snack
        open={openSnack}
        setOpen={setOpenSnack}
        alertMessage={alertMessage}
      />
    </>
  );
}
