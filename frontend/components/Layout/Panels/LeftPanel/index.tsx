import {
  Avatar,
  Grid,
  MenuList,
  Paper,
  Stack,
  Typography,
} from "@mui/material";
import { useRouter } from "next/router";
import { useState } from "react";
import { ArtistForm, SubmissionForm, TagForm } from "../../../Forms";
import CharacterForm from "../../../Forms/CharacterForm";
import ArtistAccordion from "./Accordions/ArtistAccordion";
import CharacterAccordion from "./Accordions/CharacterAccordion";
import CreateMenu from "./Menus/CreateMenu";
import SettingsMenu from "./Menus/Menu";

export default function LeftPanel() {
  const [openArtistForm, setOpenArtistForm] = useState<boolean>(false);
  const [openSubmissionForm, setOpenSubmissionForm] = useState<boolean>(false);
  const [openTagForm, setOpenTagForm] = useState<boolean>(false);
  const [openCharacterForm, setOpenCharacterForm] = useState<boolean>(false);

  const router = useRouter();

  return (
    <Paper sx={{ minHeight: "100vh", position: "sticky", top: 0 }} elevation={0}>
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
                setOpenSubmissionForm={setOpenSubmissionForm}
                setOpenTagForm={setOpenTagForm}
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
