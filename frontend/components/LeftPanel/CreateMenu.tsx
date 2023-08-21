import { MenuItem, Typography, Menu, Stack } from "@mui/material";

import PersonIcon from "@mui/icons-material/Person";
import LocalOfferIcon from "@mui/icons-material/LocalOffer";
import PhotoIcon from "@mui/icons-material/Photo";

interface Props {
  setOpenArtistForm: React.Dispatch<React.SetStateAction<boolean>>;
  setOpenSubmissionForm: React.Dispatch<React.SetStateAction<boolean>>;
  setOpenTagForm: React.Dispatch<React.SetStateAction<boolean>>;
  anchorCreateMenu: HTMLElement | null;
  setAnchorCreateMenu: React.Dispatch<React.SetStateAction<HTMLElement | null>>;
}
export default function CreateMenu(props: Props) {
  const handleCloseCreateMenu = () => {
    props.setAnchorCreateMenu(null);
  };
  return (
    <Menu
      sx={{ mt: "45px" }}
      id="menu-appbar"
      anchorEl={props.anchorCreateMenu}
      anchorOrigin={{
        vertical: "top",
        horizontal: "left",
      }}
      keepMounted
      transformOrigin={{
        vertical: "top",
        horizontal: "left",
      }}
      open={Boolean(props.anchorCreateMenu)}
      onClose={handleCloseCreateMenu}
      color="white"
    >
      <MenuItem
        onClick={() => {
          props.setOpenArtistForm(true);
          handleCloseCreateMenu();
        }}
      >
        <Stack direction="row" spacing={2}>
          <PersonIcon />
          <Typography textAlign="center">Artist</Typography>
        </Stack>
      </MenuItem>
      <MenuItem
        onClick={() => {
          props.setOpenSubmissionForm(true);
          handleCloseCreateMenu();
        }}
      >
        <Stack direction="row" spacing={2}>
          <PhotoIcon />
          <Typography textAlign="center">Submission</Typography>
        </Stack>
      </MenuItem>
      <MenuItem
        onClick={() => {
          props.setOpenTagForm(true);
          handleCloseCreateMenu();
        }}
      >
        <Stack direction="row" spacing={2}>
          <LocalOfferIcon />
          <Typography textAlign="center">Tag</Typography>
        </Stack>
      </MenuItem>
    </Menu>
  );
}
