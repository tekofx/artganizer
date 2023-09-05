import { useRouter } from "next/router";
import { useEffect, useState, useContext } from "react";
import {
  IconButton,
  Paper,
  Stack,
  Typography,
  Grid,
  Dialog,
  DialogTitle,
  TextField,
  DialogContent,
  Button,
} from "@mui/material";
import LocalOfferIcon from "@mui/icons-material/LocalOffer";
import EditIcon from "@mui/icons-material/Edit";
import CheckIcon from "@mui/icons-material/Check";
import ClearIcon from "@mui/icons-material/Clear";
import axios from "axios";
import Gallery from "../../components/Gallery";
import { TwitterPicker } from "react-color";
import Tag from "../../interfaces/Tag";
import { DataContext } from "../_app";

export default function Page() {
  const [tag, setTag] = useState<Tag>();
  const [open, setOpen] = useState(false);
  const [color, setColor] = useState("#fff");
  const [textFieldError, setTextFieldError] = useState<boolean>(false);
  const [newTag, setNewTag] = useState<string>("New Name");
  const { data, setData } = useContext(DataContext);

  const router = useRouter();

  const handleColorChange = (color: any) => {
    setColor(color.hex);
  };

  const onTextFieldChange = (event: any) => {
    setNewTag(event.target.value);
    if (event.target.value == "") {
      setTextFieldError(true);
    } else {
      setTextFieldError(false);
    }
  };

  async function editLabel() {
    const response = await axios.put(
      process.env.API_URL + `/tags/${router.query.slug}`,
      {
        name: newTag,
        color: color,
      },
      {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "Content-Type": "application/json",
        },
      }
    );
    setTag(response.data);
    data.tags.filter((tag: Tag) => {
      if (tag.id == response.data.id) {
        const newData = { ...data };
        newData.tags[data.tags.indexOf(tag)] = response.data;
        setData(newData);
      }
    });
    setOpen(false);
  }

  useEffect(() => {
    const slug = router.query.slug;
    if (slug) {
      var id = parseInt(slug.toString());

      // Get tag
      data.tags.filter((tag: Tag) => {
        if (tag.id == id) {
          setTag(tag);
          setNewTag(tag.name);
          // If tag not in filters add it
          if (!data.filters.tags.some((filterTag) => filterTag.id === tag.id)) {
            const newData = { ...data };
            newData.filters.tags.push(tag);
            setData(newData);
          }
        }
      });
    }
  }, [router.query.slug]);

  return (
    <Paper>
      <Grid container justifyContent="space-between" alignItems="center">
        <Grid item>
          <Stack spacing={2} direction="row">
            <LocalOfferIcon style={{ fontSize: "4rem", color: tag?.color }} />
            <Typography variant="h1">{tag?.name}</Typography>
          </Stack>
        </Grid>
        <Grid item>
          <IconButton onClick={() => setOpen(true)}>
            <EditIcon style={{ fontSize: "2rem" }} />
          </IconButton>
        </Grid>
      </Grid>

      <Dialog open={open}>
        <DialogTitle>Edit Tag</DialogTitle>
        <DialogContent>
          <Stack spacing={2} direction="row" alignItems="center">
            <Typography>Name</Typography>
            <TextField
              fullWidth
              variant="outlined"
              error={textFieldError}
              helperText={textFieldError ? "Label name cannot be empty" : ""}
              value={newTag}
              onChange={onTextFieldChange}
            />
          </Stack>
          <br />

          <Stack spacing={2} direction="row" alignItems="center">
            <Typography>Color</Typography>
            <TwitterPicker
              onChange={handleColorChange}
              color={color}
              triangle="hide"
            />
          </Stack>
          <br />

          <Stack spacing={2} direction="row" justifyContent="center">
            <Button startIcon={<CheckIcon />} onClick={editLabel}>
              Save
            </Button>
            <Button startIcon={<ClearIcon />} onClick={() => setOpen(false)}>
              Cancel
            </Button>
          </Stack>
        </DialogContent>
      </Dialog>

      <Gallery />
    </Paper>
  );
}
