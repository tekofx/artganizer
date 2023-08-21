import { Stack, Typography, Icon, Chip } from "@mui/material";
import Tag from "../../interfaces/Tag";
import LocalOffer from "@mui/icons-material/LocalOffer";

interface TagLabelProps {
  tag: Tag;
}

export default function TagLabel(props: TagLabelProps) {
  return (
    <Stack direction="row" spacing={1} alignItems="center">
      <Icon sx={{ color: props.tag.color }}>
        <LocalOffer />
      </Icon>

      <Typography>{props.tag.name}</Typography>
    </Stack>
  );
}
