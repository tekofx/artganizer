import { useRouter } from "next/router";
import { Typography, Stack, MenuItem, Icon, Chip } from "@mui/material";
import LocalOfferIcon from "@mui/icons-material/LocalOffer";
import Tag from "../interfaces/Tag";

interface TagListProps {
  tags: Tag[] | undefined;
}

export default function TagList(props: TagListProps) {
  const router = useRouter();

  return (
    <>
      {props.tags == undefined && <Typography>No tags</Typography>}
      {props.tags?.length == 0 && <Typography>No tags</Typography>}
      {props.tags?.map((tag) => (
        <Stack direction="row" spacing={1}>
          <Chip label={tag.name} sx={{ backgroundColor: tag.color }} />
        </Stack>
      ))}
    </>
  );
}
