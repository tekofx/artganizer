import { Typography, Stack } from "@mui/material";
import Tag from "../../interfaces/Tag";
import TagChip from "./TagChip";
interface TagListProps {
  tags: Tag[] | undefined;
}

export default function TagList(props: TagListProps) {
  return (
    <>
      {props.tags == undefined && <Typography>No tags</Typography>}
      {props.tags?.length == 0 && <Typography>No tags</Typography>}
      <Stack direction="row" spacing={1}>
        {props.tags?.map((tag) => (
          <TagChip key={tag.id} tag={tag} />
        ))}
      </Stack>
    </>
  );
}
