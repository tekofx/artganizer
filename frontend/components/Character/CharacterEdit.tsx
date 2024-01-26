import DoneIcon from "@mui/icons-material/Done";
import { Avatar, Button, Grid, Stack, TextField } from "@mui/material";
import axios from "axios";
import { Dispatch, SetStateAction, useState } from "react";
import Character from "../../interfaces/Character";
interface CharacterEditProps {
  character: Character;
  toggleEdit: () => void;
  setCharacter: Dispatch<SetStateAction<Character>>;
}

export default function CharacterEdit({ character, toggleEdit, setCharacter }: CharacterEditProps) {
  const [image, setImage] = useState<string>(character.image);
  const [imageData, setImageData] = useState<any>();

  function onImageUpload(event: any) {
    setImageData(event.target.files[0]);

    // Change the image in the preview
    setImage(URL.createObjectURL(event.target.files[0]));
  }
  async function editCharacter() {
    const formData = new FormData();
    formData.append("image", imageData);
    formData.append("name", character.name);
    formData.append("description", character.description);
    console.log(character.id);

    await axios
      .put(process.env.API_URL + `/characters/${character.id}`, formData)
      .then((response) => {
        setCharacter(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
    toggleEdit();

  }

  return (
    <Grid container spacing={2} sx={{ p: 4 }}>
      <Grid item>
        <Stack spacing={2} direction="column">
          <Avatar sx={{ width: "10rem", height: "10rem" }} src={image} />
          <Button variant="contained" component="label">
            Change image
            <input type="file" hidden onChange={onImageUpload} />
          </Button>
        </Stack>
      </Grid>
      <Grid item>
        <Stack spacing={2}>
          <TextField
            label="Name"
            value={character?.name}
            onChange={(event) => {
              if (character) {
                setCharacter((prevCharacter) => ({
                  ...prevCharacter,
                  name: event.target.value,
                }));
              }
            }}
          />

          <TextField
            label="Description"
            multiline
            value={character?.description}
            onChange={(event) => {
              if (character) {
                setCharacter((prevCharacter) => ({
                  ...prevCharacter,
                  description: event.target.value,
                }));
              }
            }}
          />
        </Stack>
      </Grid>
      <Grid item xs></Grid>
      <Grid item>
        <Stack direction="row" width="100%" spacing={2} justifyContent="center">
          <Button
            variant="contained"
            startIcon={<DoneIcon />}
            onClick={editCharacter}
          >
            Ok
          </Button>
        </Stack>
      </Grid>
    </Grid>
  );
}
