import { Grid, Avatar, Typography, Button, Stack } from "@mui/material";
import Artist from "../../../interfaces/Artist";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import EditIcon from "@mui/icons-material/Edit";

import Socials from "./Socials";
import SocialIcon from "../../SocialIcon";
interface ArtistInfoProps {
  artist?: Artist;
  toggleEdit: () => void;
  handleClickOpenDialog: () => void;
}

export default function ArtistInfo(props: ArtistInfoProps) {
  console.log(props.artist?.socials);
  return (
    <Grid container spacing={2} sx={{ p: 4 }}>
      <Grid item>
        <Avatar
          sx={{ width: "10rem", height: "10rem" }}
          src={props.artist?.image}
        />
      </Grid>
      <Grid item>
        <Typography variant="h4">{props.artist?.name}</Typography>
        <Typography variant="h5">{props.artist?.description}</Typography>
        <Socials socials={props.artist?.socials} />
      </Grid>
      <Grid item>
        <Stack direction="row" width="100%" spacing={2} justifyContent="center">
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
