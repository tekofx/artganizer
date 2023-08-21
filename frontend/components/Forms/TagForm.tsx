import {
  Stack,
  TextField,
  Grid,
  Typography,
  Button,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
} from "@mui/material";
import TagChip from "../Tag/TagChip";
import TagLabel from "../Tag/TagLabel";
import Tag from "../../interfaces/Tag";
import { useState, useContext } from "react";
import axios from "axios";
import { TwitterPicker, ColorResult } from "react-color";
import { DataContext } from "../../pages/_app";
import AlertMessage from "../../interfaces/AlertMessage";

const defaultTag: Tag = {
  name: "",
  color: "#FFFFFF",
  id: -1,
  submissionCount: 0,
};

interface Props {
  open: boolean;
  setOpen: React.Dispatch<React.SetStateAction<boolean>>;
  setAlertMessage: React.Dispatch<React.SetStateAction<AlertMessage>>;
  setOpenSnack: React.Dispatch<React.SetStateAction<boolean>>;
}

export default function TagForm(props: Props) {
  const [tag, setTag] = useState<Tag>(defaultTag);

  const { data, setData } = useContext(DataContext);

  const handleChangeComplete = (color: ColorResult) => {
    setTag((prevTag) => ({
      ...prevTag,
      color: color.hex,
    }));
  };

  async function postTag(tag: Tag) {
    await axios
      .post(`http://localhost:3001/tags`, tag)
      .then((response) => {
        const newData = { ...data };
        newData.tags.push(response.data);
        setData(newData);
        props.setAlertMessage({
          message: "Tag created",
          severity: "success",
        });
      })
      .catch((error) => {
        props.setAlertMessage({
          message: "Error creating tag",
          severity: "error",
        });

        console.log(error);
      })
      .finally(() => {
        props.setOpenSnack(true);
        props.setOpen(false);
      });
  }

  return (
    <>
      <Dialog open={props.open} onClose={() => props.setOpen(false)}>
        <DialogTitle>Create Tag</DialogTitle>
        <DialogContent>
          <Grid container spacing={2} paddingTop={2}>
            <Grid item lg={6}>
              <TextField
                label="Name"
                value={tag.name}
                fullWidth
                onChange={(event) => {
                  setTag((prevSubmission) => ({
                    ...prevSubmission,
                    name: event.target.value,
                  }));
                }}
              />
            </Grid>
            <Grid item lg={6}>
              <TwitterPicker
                triangle="hide"
                color={tag.color}
                onChangeComplete={handleChangeComplete}
                styles={{
                  default: {
                    card: {
                      boxShadow: "none",
                      backgroundColor: "transparent",
                    },
                    body: {
                      padding: 0,
                    },
                  },
                }}
              />
            </Grid>
            <Grid item lg={6}>
              <Typography>Preview</Typography>
              <Stack direction="row" spacing={5}>
                <TagLabel tag={tag} />
                <TagChip tag={tag} />
              </Stack>
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <Button variant="contained" onClick={() => postTag(tag)}>
            Create
          </Button>

          <Button onClick={() => props.setOpen(false)}>Close</Button>
        </DialogActions>
      </Dialog>
    </>
  );
}
