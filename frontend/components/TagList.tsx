import { useRouter } from "next/router";
import { Typography, Stack, MenuItem, Icon } from "@mui/material";
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
        <MenuItem key={tag.id} onClick={() => router.push(`/tag/${tag.id}`)}>
          <Stack direction="row" spacing={1}>
            <Icon sx={{ color: tag.color }}>
              <LocalOfferIcon />
            </Icon>

            <Typography>{tag.name}</Typography>
          </Stack>
        </MenuItem>
      ))}
    </>
  );
}
