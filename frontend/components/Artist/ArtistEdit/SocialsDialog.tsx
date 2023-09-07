import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Typography,
} from "@mui/material";
import { Artist } from "../../../interfaces";
import Socials from "../../Forms/ArtistForm/Socials";
interface SocialsDialogProps {
  open: boolean;
  setOpen: any;
  artist: Artist;
  setArtist: any;
}

export default function SocialsDialog({
  open,
  setOpen,
  artist,
  setArtist,
}: SocialsDialogProps) {
  return (
    <Dialog open={open}>
      <DialogTitle>
        <Typography variant="h6">Socials</Typography>
      </DialogTitle>
      <DialogContent>
        <Socials artist={artist} setArtist={setArtist} />
      </DialogContent>
      <DialogActions>
        <Button
          onClick={() => {
            setOpen(false);
          }}
        >
          Done
        </Button>
        <Button onClick={() => setOpen(false)}>Cancel</Button>
      </DialogActions>
    </Dialog>
  );
}
