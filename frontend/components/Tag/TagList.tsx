import {
  Checkbox,
  Button,
  MenuItem,
  Stack,
  Paper,
  Popover,
  Grid,
  Typography,
  Chip,
  TextField,
} from "@mui/material";
import Tag from "../../interfaces/Tag";

interface Props {
  tags: Tag[];
  onClick: (tag: Tag) => void;
}

export default function TagList(props: Props) {
  return (
    <>
      {props.tags.map((tag) => (
        <MenuItem key={tag.id} sx={{ p: 0 }} onClick={() => props.onClick(tag)}>
          <Stack direction="row" alignItems="center" spacing={1}>
            <Checkbox value={tag} checked={data.filters.tags.includes(tag)} />
            <Typography>{tag.name}</Typography>
            <Chip label={tag.submissionCount} size="small" />
          </Stack>
        </MenuItem>
      ))}
    </>
  );
}
