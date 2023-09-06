import {
  Dialog,
  DialogContent,
  DialogTitle,
  IconButton,
  Stack,
  Typography,
} from "@mui/material";
import { useState, useContext, Dispatch, SetStateAction } from "react";
import Tag from "../../interfaces/Tag";
import TagLabel from "../../components/Tag/TagLabel";
import axios from "axios";
import DeleteIcon from "@mui/icons-material/Delete";
import ClearIcon from "@mui/icons-material/Clear";
import TagForm from "./TagForm";
import EditIcon from "@mui/icons-material/Edit";
import { DataContext } from "../../pages/_app";

interface ManageTagsProps {
  open: boolean;
  setOpen: Dispatch<SetStateAction<boolean>>;
}
export default function ManageTags(props: ManageTagsProps) {
  const { data, setData } = useContext(DataContext);
  const [tags, setTags] = useState<Tag[]>(data.tags);
  const [openTagForm, setOpenTagForm] = useState<boolean>(false);

  async function onClickRemoveTag(tag: Tag) {
    await axios
      .delete(process.env.API_URL + `/tags/${tag.id}`)
      .then(() => {
        setTags(tags.filter((t) => t.id != tag.id));
        var submissionUpdated = data.submissions.map((s) => {
          var index = s.tags.findIndex((t) => t.id == tag.id);
          if (index != -1) {
            s.tags.splice(index, 1);
          }
          return s;
        });

        setData({
          ...data,
          tags: data.tags.filter((t) => t.id != tag.id),
          submissions: submissionUpdated,
        });
      })
      .catch((err) => {
        console.log(err);
      });
  }

  function onClickEditTag() {
    setOpenTagForm(true);
  }

  return (
    <>
      <Dialog open={props.open}>
        <DialogTitle>
          <Stack
            direction="row"
            spacing={2}
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
        <DialogContent sx={{ p: 5 }}>
          {tags?.map((tag) => (
            <>
              <Stack
                direction="row"
                spacing={3}
                key={tag.id}
                justifyContent="space-between"
                alignItems="center"
              >
                <TagLabel tag={tag} showSubmissions />
                <div>
                  <IconButton size="small" onClick={onClickEditTag}>
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
              <TagForm
                open={openTagForm}
                setOpen={setOpenTagForm}
                tagToUpdate={tag}
              />
            </>
          ))}
        </DialogContent>
      </Dialog>
    </>
  );
}
