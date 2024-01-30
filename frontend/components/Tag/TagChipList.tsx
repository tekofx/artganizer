import { Grid, Typography } from "@mui/material";
import Tag from "../../interfaces/Tag";
import TagChip from "./TagChip";
interface TagListProps {
  tags: Tag[] | undefined;
}

export default function TagList(props: TagListProps) {
  return (
    <Grid container spacing={1}>
      {props.tags == undefined && <Typography>No tags</Typography>}
      {props.tags?.length == 0 && <Typography>No tags</Typography>}
      {props.tags?.map((tag) => (
        <Grid item key={tag.id}>
          <TagChip tag={tag} />
        </Grid>
      ))}
    </Grid>
  );
}
