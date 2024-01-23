import {
  Stack,
  TextField,
  Grid,
  Button,
  IconButton,
  Avatar,
  Dialog,
  DialogTitle,
  DialogActions,
  DialogContent,
} from "@mui/material";
import ProgressButton from "../ProgressButon";
import { useState, useEffect } from "react";
import axios from "axios";
import LimitedTextField from "../../LimitedTextField";
import Character from "../../../interfaces/Character";
import { emptyCharacter } from "../../../src/emptyEntities";
import Snack from "../../Snack";
import { AlertMessage } from "../../../interfaces";

interface Props {
  open: boolean;
  setOpen: React.Dispatch<React.SetStateAction<boolean>>;
  name?: string;
}

export default function CharacterForm({ open, setOpen, name }: Props) {
  const [character, setCharacter] = useState<Character>(
    name ? { ...emptyCharacter, name: name } : { ...emptyCharacter }
  );
  const [image, setImage] = useState<string>("/placeholder.jpg");
  const [loading, setLoading] = useState<boolean>(false);
  const [openSnack, setOpenSnack] = useState<boolean>(false);
  const [alertMessage, setAlertMessage] = useState<AlertMessage>({
    message: "Submission created",
    severity: "success",
  });

  useEffect(() => {
    setCharacter(
      name ? { ...emptyCharacter, name: name } : { ...emptyCharacter }
    );
  }, [name]);

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
    const formData = new FormData();
    formData.append("name", character.name);
    formData.append("description", character.description);
    formData.append("image", character.image);

    await axios
      .post(process.env.API_URL + "/characters", formData)
      .then(() => {
        setAlertMessage?.({
          message: "Character created",
          severity: "success",
        });
      })
      .catch((error) => {
        console.log(error);
        setAlertMessage({
          message: "Error creating character",
          severity: "error",
        });
      })
      .finally(() => {
        setLoading(false);
        setOpen(false);
        setOpenSnack(true);
        resetForm();
      });
  }

  function onCancel() {
    setOpen(false);
    resetForm();
  }

  return (
    <>
      <Dialog open={open} onClose={() => setOpen(false)}>
        <DialogTitle>Create Character</DialogTitle>
        <DialogContent sx={{ p: 2 }}>
          <Grid container spacing={2}>
            <Grid item lg={4}>
              <input
                accept="image/*"
                id="character-form"
                multiple
                type="file"
                hidden
                onChange={onImageUpload}
              />
              <label htmlFor="character-form">
                <IconButton component="span">
                  <Avatar src={image} sx={{ width: "8rem", height: "8rem" }} />
                </IconButton>
              </label>
            </Grid>
            <Grid item lg={8}>
              <Stack spacing={2}>
                <TextField
                  label="Name"
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
                  maxLength={200}
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
          <ProgressButton
            loading={loading}
            disabled={character.name == ""}
            onClick={postCharacter}
            text="Create"
          />
          <Button disabled={loading} onClick={onCancel}>
            Cancel
          </Button>
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
