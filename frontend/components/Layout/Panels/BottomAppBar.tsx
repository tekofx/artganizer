import MoreIcon from "@mui/icons-material/MoreVert";
import SearchIcon from "@mui/icons-material/Search";
import { BottomNavigation, BottomNavigationAction } from "@mui/material";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Fab from "@mui/material/Fab";
import IconButton from "@mui/material/IconButton";
import Toolbar from "@mui/material/Toolbar";
import { styled } from "@mui/material/styles";
import Menu from "../Menu";

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
    <AppBar position="fixed" color="primary" sx={{ top: "auto", bottom: 0 }}>
      <Toolbar>
        <BottomNavigation showLabels>
          <BottomNavigationAction label="Artists" icon={<SearchIcon />} />
          <BottomNavigationAction label="Characters" icon={<SearchIcon />} />
          <BottomNavigationAction label="Submissions" icon={<SearchIcon />} />
          <BottomNavigationAction label="Tags" icon={<SearchIcon />} />
        </BottomNavigation>
        <Menu />
        <StyledFab color="secondary" aria-label="add">
          <SearchIcon />
        </StyledFab>
        <Box sx={{ flexGrow: 1 }} />
        <IconButton color="inherit">
          <SearchIcon />
        </IconButton>
        <IconButton color="inherit">
          <MoreIcon />
        </IconButton>
      </Toolbar>
    </AppBar>
  );
}
