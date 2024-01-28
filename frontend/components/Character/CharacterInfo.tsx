import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import EditIcon from "@mui/icons-material/Edit";
import { Avatar, Button, Grid, Stack, Typography } from "@mui/material";
import Character from "../../interfaces/Character";
interface CharacterInfoProps {
  character?: Character;
  toggleEdit: () => void;
  handleClickOpenDialog: () => void;
}

export default function CharacterInfo(props: CharacterInfoProps) {
  return (
    <Grid container spacing={2} sx={{ p: 4 }}>
      <Grid item>
        <Avatar
          sx={{ width: "10rem", height: "10rem" }}
          src={props.character?.image}
        />
      </Grid>
      <Grid item>
        <Typography variant="h4">{props.character?.name}</Typography>
        <Typography variant="h5">{props.character?.description}</Typography>
      </Grid>
      <Grid item xs></Grid>
      <Grid item>
        <Stack direction="column" width="100%" spacing={2} justifyContent="center">
          <Button
            variant="contained"
            startIcon={<EditIcon />}
            onClick={props.toggleEdit}
          >
            Edit
          </Button>

          <Button
            variant="contained"
            startIcon={<DeleteForeverIcon />}
            onClick={props.handleClickOpenDialog}
          >
            Remove
          </Button>
        </Stack>
      </Grid>
    </Grid>
  );
}
