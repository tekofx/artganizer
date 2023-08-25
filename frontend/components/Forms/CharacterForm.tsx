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
import { useState, useContext } from "react";
import { DataContext } from "../../pages/_app";
import axios from "axios";
import LimitedTextField from "../LimitedTextField";
import Character from "../../interfaces/Character";
import { emptyCharacter } from "../../src/utils";
interface AlertMessage {
  message: string;
  severity: "success" | "error" | "info" | "warning";
}

interface Props {
  open: boolean;
  setOpen: React.Dispatch<React.SetStateAction<boolean>>;
  setOpenSnack: React.Dispatch<React.SetStateAction<boolean>>;
  setAlertMessage: React.Dispatch<React.SetStateAction<AlertMessage>>;
}

export default function CharacterForm(props: Props) {
  const [character, setCharacter] = useState<Character>(emptyCharacter);
  const [image, setImage] = useState<string>("/placeholder.jpg");

  const { data, setData } = useContext(DataContext);

  function onImageUpload(event: any) {
    const newCharacter = { ...character };
    newCharacter.image = event.target.files[0];
    setCharacter(newCharacter);
    setImage(URL.createObjectURL(event.target.files[0]));
  }

  async function postCharacter(character: Character) {
    const formData = new FormData();
    formData.append("name", character.name);
    formData.append("description", character.description);
    formData.append("image", character.image);

    await axios
      .post("http://localhost:3001/characters", formData)
      .then((response) => {
        var newData = { ...data };
        newData.characters.push(response.data);
        setData(newData);
        props.setAlertMessage({
          message: "Character created",
          severity: "success",
        });
      })
      .catch((error) => {
        console.log(error);
        props.setAlertMessage({
          message: "Error creating character",
          severity: "error",
        });
      })
      .finally(() => {
        props.setOpenSnack(true);
      });
  }

  return (
    <Dialog open={props.open} onClose={() => props.setOpen(false)}>
      <DialogTitle>Create Character</DialogTitle>
      <DialogContent sx={{ p: 2 }}>
        <Grid container spacing={2}>
          <Grid item lg={4}>
            <input
              accept="image/*"
              id="contained-button-file"
              multiple
              type="file"
              hidden
              onChange={onImageUpload}
            />
            <label htmlFor="contained-button-file">
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
        <Button
          variant="contained"
          onClick={() => {
            postCharacter(character);
            props.setOpen(false);
          }}
        >
          Create
        </Button>
        <Button onClick={() => props.setOpen(false)}>Cancel</Button>
      </DialogActions>
    </Dialog>
  );
}
