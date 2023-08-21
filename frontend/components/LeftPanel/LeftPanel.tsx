import { Grid, Typography, Paper, MenuList } from "@mui/material";
import { useState } from "react";
import { useRouter } from "next/router";
import FolderAccordion from "./FolderAccordion";
import ArtistAccordion from "./ArtistAccordion";
import Snack from "../Snack";
import AlertMessage from "../../interfaces/AlertMessage";
import { ArtistForm, SubmissionForm, TagForm } from "../Forms";
import CreateMenu from "./CreateMenu";
import SettingsMenu from "./SettingsMenu";
import ManageTags from "../Forms/ManageTags";

export default function LeftPanel() {
  const [openArtistForm, setOpenArtistForm] = useState<boolean>(false);
  const [openSubmissionForm, setOpenSubmissionForm] = useState<boolean>(false);
  const [openTagForm, setOpenTagForm] = useState<boolean>(false);
  const [openSnack, setOpenSnack] = useState<boolean>(false);
  const [alertMessage, setAlertMessage] = useState<AlertMessage>({
    message: "Submission created",
    severity: "success",
  });

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
              <Typography
                variant="h5"
                onClick={() => router.push("/")}
                sx={{ cursor: "pointer" }}
              >
                Artganizer
              </Typography>
            </Grid>
            <Grid item>
              <CreateMenu
                setOpenArtistForm={setOpenArtistForm}
                setOpenSubmissionForm={setOpenSubmissionForm}
                setOpenTagForm={setOpenTagForm}
              />
            </Grid>
          </Grid>
        </Grid>
        <Grid item lg={12}>
          <MenuList>
            <FolderAccordion />
            <ArtistAccordion />
          </MenuList>
        </Grid>
      </Grid>
      <ArtistForm
        open={openArtistForm}
        setOpen={setOpenArtistForm}
        setAlertMessage={setAlertMessage}
        setOpenSnack={setOpenSnack}
      />
      <SubmissionForm
        open={openSubmissionForm}
        setOpen={setOpenSubmissionForm}
        setOpenSnack={setOpenSnack}
        setAlertMessage={setAlertMessage}
      />

      <TagForm
        open={openTagForm}
        setOpen={setOpenTagForm}
        setAlertMessage={setAlertMessage}
        setOpenSnack={setOpenSnack}
      />
      <Snack
        open={openSnack}
        setOpen={setOpenSnack}
        alertMessage={alertMessage}
      />
    </Paper>
  );
}
