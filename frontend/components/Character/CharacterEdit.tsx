import DoneIcon from "@mui/icons-material/Done";
import { Avatar, Button, Grid, Stack } from "@mui/material";
import { Dispatch, SetStateAction, useState } from "react";
import Character from "../../interfaces/Character";
import { useAppContext } from "../../pages/_app";
import LimitedTextField from "../LimitedTextField";
interface CharacterEditProps {
  character: Character;
  toggleEdit: () => void;
  setCharacter: Dispatch<SetStateAction<Character>>;
}

export default function CharacterEdit({ character, toggleEdit, setCharacter }: CharacterEditProps) {
  const [image, setImage] = useState<string>(character.image);
  const { editCharacter } = useAppContext();

  function onImageUpload(event: any) {
    const newCharacter = { ...character };
    newCharacter.image = event.target.files[0];
    setCharacter(newCharacter);

    // Change the image in the preview
    setImage(URL.createObjectURL(event.target.files[0]));
  }
  async function onOkClick() {
    var result = await editCharacter(character);
    if (result) {
      setCharacter(result);
    }
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
          <LimitedTextField
            maxLength={20}
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

          <LimitedTextField
            maxLength={200}
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
            onClick={onOkClick}
          >
            Ok
          </Button>
        </Stack>
      </Grid>
    </Grid>
  );
}
