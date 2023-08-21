import {
  MenuItem,
  Typography,
  Menu,
  Stack,
  IconButton,
  Tooltip,
} from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import PersonIcon from "@mui/icons-material/Person";
import LocalOfferIcon from "@mui/icons-material/LocalOffer";
import PhotoIcon from "@mui/icons-material/Photo";
import { useState, MouseEvent } from "react";

interface Props {
  setOpenArtistForm: React.Dispatch<React.SetStateAction<boolean>>;
  setOpenSubmissionForm: React.Dispatch<React.SetStateAction<boolean>>;
  setOpenTagForm: React.Dispatch<React.SetStateAction<boolean>>;
}
export default function CreateMenu(props: Props) {
  const [anchorCreateMenu, setAnchorCreateMenu] = useState<null | HTMLElement>(
    null
  );
  const handleCloseCreateMenu = () => {
    setAnchorCreateMenu(null);
  };
  const handleOpenCreateMenu = (event: MouseEvent<HTMLElement>) => {
    setAnchorCreateMenu(event.currentTarget);
  };

  return (
    <>
      <Tooltip title="Create">
        <IconButton onClick={handleOpenCreateMenu} sx={{ p: 0 }}>
          <AddIcon />
        </IconButton>
      </Tooltip>
      <Menu
        sx={{ mt: "45px" }}
        id="menu-appbar"
        anchorEl={anchorCreateMenu}
        anchorOrigin={{
          vertical: "top",
          horizontal: "left",
        }}
        keepMounted
        transformOrigin={{
          vertical: "top",
          horizontal: "left",
        }}
        open={Boolean(anchorCreateMenu)}
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
    </>
  );
}
