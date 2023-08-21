import {
  Chip,
  Dialog,
  DialogContent,
  DialogTitle,
  IconButton,
  Paper,
  Stack,
  Typography,
} from "@mui/material";
import TagList from "../../components/Tag/TagList";
import {
  useState,
  useEffect,
  useContext,
  Dispatch,
  SetStateAction,
} from "react";
import Tag from "../../interfaces/Tag";
import TagLabel from "../../components/Tag/TagLabel";
import axios from "axios";
import DeleteIcon from "@mui/icons-material/Delete";
import ClearIcon from "@mui/icons-material/Clear";

import EditIcon from "@mui/icons-material/Edit";
import { DataContext } from "../../pages/_app";

interface ManageTagsProps {
  open: boolean;
  setOpen: Dispatch<SetStateAction<boolean>>;
}
export default function ManageTags(props: ManageTagsProps) {
  const { data, setData } = useContext(DataContext);
  const [tags, setTags] = useState<Tag[]>(data.tags);

  async function onClickRemoveTag(tag: Tag) {
    await axios
      .delete(`http://localhost:3001/tags/${tag.id}`)
      .then(() => {
        setTags(tags.filter((t) => t.id != tag.id));
        setData({ ...data, tags: data.tags.filter((t) => t.id != tag.id) });
      })
      .catch((err) => {
        console.log(err);
      });
  }

  return (
    <Dialog open={props.open} sx={{ p: 2 }}>
      <DialogTitle>
        <Stack
          direction="row"
          spacing={1}
          alignItems="center"
          justifyContent="space-between"
        >
          <Typography variant="h5">Manage tags</Typography>
          <IconButton
            onClick={() => {
              props.setOpen(false);
            }}
          >
            <ClearIcon />
          </IconButton>
        </Stack>
      </DialogTitle>
      <DialogContent>
        {tags?.map((tag) => (
          <Stack
            direction="row"
            spacing={1}
            key={tag.id}
            justifyContent="space-between"
            alignItems="center"
          >
            <TagLabel tag={tag} showSubmissions />
            <div>
              <IconButton size="small">
                <EditIcon />
              </IconButton>
              <IconButton
                size="small"
                color="error"
                onClick={() => onClickRemoveTag(tag)}
              >
                <DeleteIcon />
              </IconButton>
            </div>
          </Stack>
        ))}
      </DialogContent>
    </Dialog>
  );
}
