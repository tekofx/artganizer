import { Typography, Stack, Chip } from "@mui/material";
import Tag from "../interfaces/Tag";

interface TagListProps {
  tags: Tag[] | undefined;
}

export default function TagList(props: TagListProps) {

  return (
    <>
      {props.tags == undefined && <Typography>No tags</Typography>}
      {props.tags?.length == 0 && <Typography>No tags</Typography>}
      {props.tags?.map((tag) => (
        <Stack direction="row" spacing={1} key={tag.id}>
          <Chip label={tag.name} sx={{ backgroundColor: tag.color }} />
        </Stack>
      ))}
    </>
  );
}
