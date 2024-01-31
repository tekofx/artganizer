import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import EditIcon from "@mui/icons-material/Edit";
import { Avatar, Button, Grid, Stack, Typography } from "@mui/material";
import Artist from "../../../interfaces/Artist";
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
    <Grid container spacing={2} sx={{ p: 4 }} >
      <Grid item xs={12} lg display={{ xs: "flex", lg: "block" }} alignItems="center" justifyContent="center">
        <Avatar sx={{ width: "10rem", height: "10rem" }} src={artist?.image} />
      </Grid>
      {/* This Grid only show in lg */}
      <Grid item xs={12} lg display={{ xs: "none", lg: "block" }} alignItems="center" justifyContent="center">
        <Typography variant="h4">{artist?.name}</Typography>
        <Typography variant="h5">{artist?.description}</Typography>
      </Grid>

      {/* This Grids only show in <lg */}
      <Grid item xs={12} lg display={{ xs: "flex", lg: "none" }} alignItems="center" justifyContent="center">
        <Typography variant="h4">{artist?.name}</Typography>
      </Grid>
      <Grid item xs={12} lg display={{ xs: "flex", lg: "none" }} alignItems="center" justifyContent="center">
        <Typography variant="h5">{artist?.description}</Typography>
      </Grid>


      <Grid item xs={12} lg display={{ xs: "flex", lg: "block" }} alignItems="center" justifyContent="center">
        {artist?.socials?.map((social, index) => (
          <SocialIcon social={social} key={index} clickable />
        ))}
      </Grid>
      <Grid item display={{ xs: "none", lg: "block" }} lg></Grid>


      {/* These buttons only show in >=lg */}
      <Grid item xs={12} lg={2} display={{ xs: "none", lg: "block" }} alignItems="center" justifyContent="center">
        <Stack spacing={2}>
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
