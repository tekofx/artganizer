import BrushIcon from "@mui/icons-material/Brush";
import LocalOfferIcon from "@mui/icons-material/LocalOffer";
import PersonIcon from "@mui/icons-material/Person";
import PhotoIcon from "@mui/icons-material/Photo";
import { BottomNavigation, BottomNavigationAction } from "@mui/material";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import CreatePopup from "./CreateButton";

export default function BottomAppBar() {
  const router = useRouter();
  const [value, setValue] = useState(0);

  const pages = [
    { id: 0, page: "Artists", navigate: "/artists", icon: <BrushIcon /> },
    {
      id: 1,
      page: "Characters",
      navigate: "/characters",
      icon: <PersonIcon />,
    },
    { id: 2, page: "Submissions", navigate: "/", icon: <PhotoIcon /> },
    { id: 3, page: "Tags", navigate: "/tags", icon: <LocalOfferIcon /> },
  ];

  useEffect(() => {
    function getIdFromPath(path: string) {
      const page = pages.find((page) => page.navigate === path);
      if (page) {
        return page.id;
      }
      return 0;
    }
    console.log(router.pathname);
    setValue(getIdFromPath(router.pathname));
  }, []);

  function onClick(event: React.ChangeEvent<{}>, newValue: number) {
    setValue(newValue);
    router.push(pages[newValue].navigate);
  }
  return (
    <AppBar
      position="fixed"
      color="primary"
      sx={{ top: "auto", bottom: 0, paddingLeft: 0, paddingRight: 0 }}
    >
      <Toolbar sx={{ top: "auto", bottom: 0, paddingLeft: 0, paddingRight: 0 }}>
        <BottomNavigation showLabels sx={{ width: "100%" }} value={value}>
          {pages.map((page) => (
            <BottomNavigationAction
              key={page.id}
              label={page.page}
              icon={page.icon}
              onClick={(event) => onClick(event, page.id)}
            />
          ))}
        </BottomNavigation>

        <CreatePopup />
      </Toolbar>
    </AppBar>
  );
}
