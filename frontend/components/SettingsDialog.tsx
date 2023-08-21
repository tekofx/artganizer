import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  FormControlLabel,
  FormGroup,
  IconButton,
  Stack,
  Typography,
} from "@mui/material";
import { Dispatch, SetStateAction } from "react";
import ClearIcon from "@mui/icons-material/Clear";
import { CheckBox } from "@mui/icons-material";

interface SettingsDialogProps {
  open: boolean;
  setOpen: Dispatch<SetStateAction<boolean>>;
}
export default function SettingsDialog(props: SettingsDialogProps) {
  return (
    <Dialog open={props.open}>
      <DialogTitle>
        <Stack
          direction="row"
          alignItems="center"
          spacing={2}
          justifyContent="space-between"
        >
          <Typography variant="h6">Settings</Typography>
          <IconButton onClick={() => props.setOpen(false)}>
            <ClearIcon />
          </IconButton>
        </Stack>
      </DialogTitle>
      <DialogContent>
        <FormGroup>
          <FormControlLabel label="Show lateral menu" control={<CheckBox />} />
          <FormControlLabel
            label="Show number of submissions in tags"
            control={<CheckBox />}
          />
        </FormGroup>
      </DialogContent>
      <DialogActions>
        <Button variant="contained" color="primary">
          Save
        </Button>
        <Button variant="outlined" onClick={() => props.setOpen(false)}>
          Close
        </Button>
      </DialogActions>
    </Dialog>
  );
}
