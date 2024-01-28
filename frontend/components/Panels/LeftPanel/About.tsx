import {
  Avatar,
  Dialog,
  DialogContent,
  DialogTitle,
  Grid,
  IconButton,
  Link,
  Paper,
  Stack,
  Typography,
} from "@mui/material";
import { Dispatch, SetStateAction } from "react";

import ClearIcon from "@mui/icons-material/Clear";
interface SettingsDialogProps {
  open: boolean;
  setOpen: Dispatch<SetStateAction<boolean>>;
}
export default function About({ open, setOpen }: SettingsDialogProps) {
  return (
    <Dialog open={open}>
      <DialogTitle>
        <Stack
          direction="row"
          alignItems="center"
          spacing={2}
          justifyContent="space-between"
        >
          <Typography variant="h6">About</Typography>
          <IconButton onClick={() => setOpen(false)}>
            <ClearIcon />
          </IconButton>
        </Stack>
      </DialogTitle>
      <DialogContent>
        <Grid container spacing={3}>
          <Grid item xs={12}>
            <Stack direction="column" spacing={2} alignItems="center">
              <Typography variant="h4">Artganizer</Typography>
              <Avatar sx={{ width: 200, height: 200 }} />
            </Stack>
          </Grid>
          <Grid item xs={6}>
            <Typography variant="body1">Made with ♥️ by</Typography>
            <Paper
              sx={{ p: 1, cursor: "pointer" }}
              onClick={() => window.open("https://github.com/tekofx", "_blank")}
            >
              <Stack direction="row" spacing={2} alignItems="center">
                <Avatar src="https://github.com/tekofx.png?size=40" />
                <Typography variant="body1">Teko</Typography>
              </Stack>
            </Paper>
          </Grid>
          <Grid item xs={6}>
            <Link href="https://github.com/tekofx/artganizer">Source Code</Link>
          </Grid>
        </Grid>
      </DialogContent>
    </Dialog>
  );
}
