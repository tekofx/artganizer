import BrushIcon from "@mui/icons-material/Brush";
import LocalOfferIcon from "@mui/icons-material/LocalOffer";
import PersonIcon from "@mui/icons-material/Person";
import PhotoIcon from "@mui/icons-material/Photo";
import SearchIcon from "@mui/icons-material/Search";
import { BottomNavigation, BottomNavigationAction } from "@mui/material";
import AppBar from "@mui/material/AppBar";
import Fab from "@mui/material/Fab";
import Toolbar from "@mui/material/Toolbar";
import { styled } from "@mui/material/styles";
const StyledFab = styled(Fab)({
  position: "absolute",
  zIndex: 1,
  top: -30,
  left: 0,
  right: 0,
  margin: "0 auto",
});

export default function BottomAppBar() {
  return (
    <AppBar
      position="fixed"
      color="primary"
      sx={{ top: "auto", bottom: 0, paddingLeft: 0, paddingRight: 0 }}
    >
      <Toolbar sx={{ top: "auto", bottom: 0, paddingLeft: 0, paddingRight: 0 }}>
        <BottomNavigation showLabels sx={{ width: "100%" }}>
          <BottomNavigationAction label="Artists" icon={<BrushIcon />} />
          <BottomNavigationAction label="Characters" icon={<PersonIcon />} />
          <BottomNavigationAction label="Submissions" icon={<PhotoIcon />} />
          <BottomNavigationAction label="Tags" icon={<LocalOfferIcon />} />
        </BottomNavigation>
        <StyledFab color="secondary" aria-label="add">
          <SearchIcon />
        </StyledFab>
        {/* <Box sx={{ flexGrow: 1 }} />
        <IconButton color="inherit">
        <SearchIcon />
        </IconButton>
        <IconButton color="inherit">
        <MoreIcon />
      </IconButton>
      <Menu /> */}
      </Toolbar>
    </AppBar>
  );
}
