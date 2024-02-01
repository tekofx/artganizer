import AddIcon from "@mui/icons-material/Add";
import BrushIcon from "@mui/icons-material/Brush";
import LocalOfferIcon from "@mui/icons-material/LocalOffer";
import PersonIcon from "@mui/icons-material/Person";

import PhotoIcon from "@mui/icons-material/Photo";
import {
  Fab,
  Menu,
  MenuItem,
  Stack,
  Tooltip,
  Typography,
  styled,
} from "@mui/material";
import { MouseEvent, useState } from "react";
import { ArtistForm, SubmissionForm, TagForm } from "../../../Forms";
import CharacterForm from "../../../Forms/CharacterForm";

export default function CreateButton() {
  const [openSubmissionForm, setOpenSubmissionForm] = useState<boolean>(false);
  const [openTagForm, setOpenTagForm] = useState<boolean>(false);
  const [openArtistForm, setOpenArtistForm] = useState<boolean>(false);
  const [openCharacterForm, setOpenCharacterForm] = useState<boolean>(false);
  const [anchorCreateMenu, setAnchorCreateMenu] = useState<null | HTMLElement>(
    null
  );
  const handleCloseCreateMenu = () => {
    setAnchorCreateMenu(null);
  };
  const handleOpenCreateMenu = (event: MouseEvent<HTMLElement>) => {
    setAnchorCreateMenu({ top: event.clientY, left: event.clientX });
  };
  const StyledFab = styled(Fab)({
    position: "absolute",
    zIndex: 1,
    top: -30,
    left: 0,
    right: 0,
    margin: "0 auto",
  });

  const MenuItems = [
    {
      name: "Artist",
      icon: <BrushIcon />,
      onClick: () => {
        setOpenArtistForm(true);
        handleCloseCreateMenu();
      },
    },
    {
      name: "Character",
      icon: <PersonIcon />,
      onClick: () => {
        setOpenCharacterForm(true);
        handleCloseCreateMenu();
      },
    },
    {
      name: "Submission",
      icon: <PhotoIcon />,
      onClick: () => {
        setOpenSubmissionForm(true);
        handleCloseCreateMenu();
      },
    },
    {
      name: "Tag",
      icon: <LocalOfferIcon />,
      onClick: () => {
        setOpenTagForm(true);
        handleCloseCreateMenu();
      },
    },
  ];

  return (
    <>
      <Tooltip title="Create">
        <StyledFab
          color="secondary"
          aria-label="add"
          onClick={handleOpenCreateMenu}
          sx={{ p: 0 }}
        >
          <AddIcon />
        </StyledFab>
      </Tooltip>
      <Menu
        sx={{ top: -80 }}
        id="menu-appbar"
        anchorReference="anchorPosition"
        anchorPosition={anchorCreateMenu || undefined}
        anchorOrigin={{
          vertical: "bottom",
          horizontal: "center",
        }}
        keepMounted
        transformOrigin={{
          vertical: "top",
          horizontal: "center",
        }}
        open={Boolean(anchorCreateMenu)}
        onClose={handleCloseCreateMenu}
        color="white"
      >
        {MenuItems.map((item, index) => (
          <MenuItem key={index} onClick={item.onClick}>
            <Stack direction="row" spacing={2}>
              {item.icon}
              <Typography textAlign="center">{item.name}</Typography>
            </Stack>
          </MenuItem>
        ))}
      </Menu>
      <SubmissionForm
        open={openSubmissionForm}
        setOpen={setOpenSubmissionForm}
      />
      <TagForm open={openTagForm} setOpen={setOpenTagForm} />
      <ArtistForm open={openArtistForm} setOpen={setOpenArtistForm} />
      <CharacterForm open={openCharacterForm} setOpen={setOpenCharacterForm} />
    </>
  );
}
