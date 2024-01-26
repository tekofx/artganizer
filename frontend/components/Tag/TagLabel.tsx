import LocalOffer from "@mui/icons-material/LocalOffer";
import { Chip, Icon, Stack, Tooltip, Typography } from "@mui/material";
import Tag from "../../interfaces/Tag";

interface TagLabelProps {
  tag: Tag;
  showSubmissions?: boolean;
}

export default function TagLabel(props: TagLabelProps) {
  return (
    <Stack direction="row" spacing={1} alignItems="center">
      {props.showSubmissions && (
        <Tooltip title="Number of submissions with this tag" arrow>
          <Chip label={props.tag.submissionCount} size="small" />
        </Tooltip>
      )}

      <Icon sx={{ color: props.tag.color }}>
        <LocalOffer />
      </Icon>

      <Typography>{props.tag.name}</Typography>
    </Stack>
  );
}
