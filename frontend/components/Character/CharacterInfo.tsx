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
      <Grid item xs={12} lg display={{ xs: "flex", lg: "block" }} alignItems="center" justifyContent="center">
        <Avatar
          sx={{ width: "10rem", height: "10rem" }}
          src={props.character?.image}
        />
      </Grid>
      {/* This Grid only show in lg */}
      <Grid item xs={12} lg display={{ xs: "none", lg: "block" }} alignItems="center" justifyContent="center">
        <Typography variant="h4">{props.character?.name}</Typography>
        <Typography variant="h5">{props.character?.description}</Typography>
      </Grid>

      {/* This Grids only show in <lg */}
      <Grid item xs={12} lg display={{ xs: "flex", lg: "none" }} alignItems="center" justifyContent="center">
        <Typography variant="h4">{props.character?.name}</Typography>
      </Grid>
      <Grid item xs={12} lg display={{ xs: "flex", lg: "none" }} alignItems="center" justifyContent="center">
        <Typography variant="h5">{props.character?.description}</Typography>
      </Grid>


      <Grid item xs={12} lg={2} display={{ xs: "none", lg: "block" }} alignItems="center" justifyContent="center">
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
