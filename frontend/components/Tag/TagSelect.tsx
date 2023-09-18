import { Dispatch, SetStateAction, useState } from "react";
import { Paper, Typography, Grid } from "@mui/material";
import SelectableTagList from "./SelectableTagList";
import Tag from "../../interfaces/Tag";
import { TagForm } from "../Forms";

interface TagSelectProps {
  setSelectedTags: Dispatch<SetStateAction<Tag[]>>;
  selectedTags: Tag[];
}
export default function TagSelect({
  selectedTags,
  setSelectedTags,
}: TagSelectProps) {
  const [openTagForm, setOpenTagForm] = useState(false);

  return (
    <Paper elevation={0} sx={{ p: 1 }}>
      <Grid container spacing={1}>
        <Grid item xs={12}>
          <Typography>Tags</Typography>
        </Grid>

        <Grid item xs={12}>
          <SelectableTagList
            selectedTags={selectedTags}
            setSelectedTags={setSelectedTags}
          />
        </Grid>

        <TagForm open={openTagForm} setOpen={setOpenTagForm} />
      </Grid>
    </Paper>
  );
}
