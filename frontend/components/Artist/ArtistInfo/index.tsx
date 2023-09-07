import { Grid, Avatar, Typography, Button, Stack } from "@mui/material";
import Artist from "../../../interfaces/Artist";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import EditIcon from "@mui/icons-material/Edit";

import SocialIcon from "../../SocialIcon";
interface ArtistInfoProps {
  artist?: Artist;
  toggleEdit: () => void;
  handleClickOpenDialog: () => void;
}

export default function ArtistInfo({
  artist,
  toggleEdit,
  handleClickOpenDialog,
}: ArtistInfoProps) {
  return (
    <Grid container spacing={2} sx={{ p: 4 }}>
      <Grid item>
        <Avatar sx={{ width: "10rem", height: "10rem" }} src={artist?.image} />
      </Grid>
      <Grid item>
        <Typography variant="h4">{artist?.name}</Typography>
        <Typography variant="h5">{artist?.description}</Typography>
        {artist?.socials?.map((social, index) => (
          <SocialIcon social={social} key={index} clickable />
        ))}
      </Grid>
      <Grid item>
        <Stack direction="row" width="100%" spacing={2} justifyContent="center">
          <Button
            variant="contained"
            startIcon={<EditIcon />}
            onClick={toggleEdit}
          >
            Edit
          </Button>

          <Button
            variant="contained"
            startIcon={<DeleteForeverIcon />}
            onClick={handleClickOpenDialog}
          >
            Remove
          </Button>
        </Stack>
      </Grid>
    </Grid>
  );
}
