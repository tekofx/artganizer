import {
  Grid,
  Typography,
  Paper,
  MenuList,
  Avatar,
  Stack,
} from "@mui/material";
import { useState } from "react";
import { useRouter } from "next/router";
import ArtistAccordion from "./Accordions/ArtistAccordion";
import { ArtistForm, SubmissionForm, TagForm } from "../../Forms";
import CreateMenu from "./Menus/CreateMenu";
import SettingsMenu from "./Menus/Menu";
import CharacterForm from "../../Forms/CharacterForm";
import CharacterAccordion from "./Accordions/CharacterAccordion";

export default function LeftPanel() {
  const [openArtistForm, setOpenArtistForm] = useState<boolean>(false);
  const [openSubmissionForm, setOpenSubmissionForm] = useState<boolean>(false);
  const [openTagForm, setOpenTagForm] = useState<boolean>(false);
  const [openCharacterForm, setOpenCharacterForm] = useState<boolean>(false);

  const router = useRouter();

  return (
    <Paper sx={{ minHeight: "100vh" }} elevation={0}>
      <Grid container sx={{ p: 2 }}>
        {/* Superior bar */}
        <Grid item lg={12}>
          <Grid container justifyContent="space-between" alignItems="center">
            <Grid item>
              <SettingsMenu />
            </Grid>

            <Grid item>
              <Stack direction="row" spacing={1} alignItems="center">
                <Avatar src="/logo.jpg" sx={{ width: 25, height: 25 }} />
                <Typography
                  variant="h5"
                  onClick={() => router.push("/")}
                  sx={{ cursor: "pointer" }}
                >
                  Artganizer
                </Typography>
              </Stack>
            </Grid>
            <Grid item>
              <CreateMenu
                setOpenArtistForm={setOpenArtistForm}
                setOpenSubmissionForm={setOpenSubmissionForm}
                setOpenTagForm={setOpenTagForm}
                setOpenCharacterForm={setOpenCharacterForm}
              />
            </Grid>
          </Grid>
        </Grid>
        <Grid item lg={12}>
          <MenuList>
            <ArtistAccordion />
            <CharacterAccordion />
          </MenuList>
        </Grid>
      </Grid>
      <ArtistForm open={openArtistForm} setOpen={setOpenArtistForm} />
      <SubmissionForm
        open={openSubmissionForm}
        setOpen={setOpenSubmissionForm}
      />

      <TagForm open={openTagForm} setOpen={setOpenTagForm} />
      <CharacterForm open={openCharacterForm} setOpen={setOpenCharacterForm} />
    </Paper>
  );
}
