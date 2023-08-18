import { Chip } from "@mui/material";
import Tag from "../../interfaces/Tag";
import HighlightOffIcon from "@mui/icons-material/HighlightOff";
interface TagChipProps {
  tag: Tag;
  onDelete?: any;
}

function getTextColor(color: string): string {
  // Remove hash symbol if present
  color = color.replace("#", "");

  // Convert to RGB value
  let r = parseInt(color.substring(0, 2), 16);
  let g = parseInt(color.substring(2, 4), 16);
  let b = parseInt(color.substring(4, 6), 16);

  // Calculate brightness
  let brightness = (r * 299 + g * 587 + b * 114) / 1000;

  // Return black or white depending on brightness
  return brightness > 128 ? "black" : "white";
}

export default function TagChip(props: TagChipProps) {
  return (
    <Chip
      label={props.tag.name}
      deleteIcon={
        <HighlightOffIcon style={{ color: getTextColor(props.tag.color) }} />
      }
      onDelete={props.onDelete}
      sx={{
        backgroundColor: props.tag.color,
        color: getTextColor(props.tag.color),
      }}
    />
  );
}
