import { Grid, Avatar, Button, Stack, TextField } from "@mui/material";
import DoneIcon from "@mui/icons-material/Done";
import { Dispatch, SetStateAction, useContext, useState } from "react";
import axios from "axios";
import { DataContext } from "../../pages/_app";
import { useRouter } from "next/router";
import Character from "../../interfaces/Character";
interface CharacterEditProps {
  character: Character;
  toggleEdit: () => void;
  setCharacter: Dispatch<SetStateAction<Character>>;
}

export default function CharacterEdit(props: CharacterEditProps) {
  const [character, setCharacter] = useState<Character>(props.character);
  const [image, setImage] = useState<string>(props.character.image);
  const [imageData, setImageData] = useState<any>();
  const { data, setData } = useContext(DataContext);
  const router = useRouter();

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
    formData.append("id", character?.id.toString());

    await axios
      .put(process.env.API_URL + `/characters/${character.id}`, formData)
      .then((response) => {
        props.setCharacter(response.data);
        var newData = { ...data };
        newData.characters = newData.characters.map((character) => {
          if (character.id === response.data.id) {
            return response.data;
          }
          return character;
        });
        setData(newData);
      })
      .catch((error) => {
        console.log(error);
      });
    props.toggleEdit();

    // Reload the page
    router.reload();
  }

  return (
    <Grid container spacing={2}>
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
      <Grid item>
        <Stack direction="row" width="100%" spacing={2} justifyContent="center">
          <Button
            variant="contained"
            startIcon={<DoneIcon />}
            onClick={() => editCharacter()}
          >
            Ok
          </Button>
        </Stack>
      </Grid>
    </Grid>
  );
}
