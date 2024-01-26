import ClearIcon from "@mui/icons-material/Clear";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import {
  Dialog,
  DialogContent,
  DialogTitle,
  IconButton,
  Stack,
  Typography,
} from "@mui/material";
import { Dispatch, SetStateAction, useState } from "react";
import TagLabel from "../../components/Tag/TagLabel";
import Tag from "../../interfaces/Tag";
import { useAppContext } from "../../pages/_app";
import TagForm from "./TagForm";

interface ManageTagsProps {
  open: boolean;
  setOpen: Dispatch<SetStateAction<boolean>>;
}
export default function ManageTags(props: ManageTagsProps) {
  const { tags } = useAppContext();

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
            <TagElement tag={tag} key={tag.id} />
          ))}
        </DialogContent>
      </Dialog>
    </>
  );
}

function TagElement({ tag }: { tag: Tag }) {
  const [openTagForm, setOpenTagForm] = useState<boolean>(false);
  const { removeTag } = useAppContext();

  async function onClickRemoveTag(tag: Tag) {
    await removeTag(tag);
  }

  function onClickEditTag() {
    setOpenTagForm(true);
  }
  return (
    <div >
      <Stack
        direction="row"
        spacing={3}
        justifyContent="space-between"
        alignItems="center"
      >
        <TagLabel tag={tag} showSubmissions />
        <div>
          <IconButton size="small" onClick={() => onClickEditTag()}>
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
    </div>
  )
}