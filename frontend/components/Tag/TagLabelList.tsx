import { Grid } from "@mui/material";
import { useAppContext } from "../../pages/_app";
import TagLabel from "./TagLabel";

interface TagLabelListProps {
  search?: string;
}

export default function TagLabelList({ search }: TagLabelListProps) {
  const { tags } = useAppContext();
  return (
    <Grid container spacing={1}>
      {tags
        .filter((tag) =>
          tag.name.toLowerCase().includes(search === undefined ? "" : search)
        )
        .map((tag) => (
          <Grid item key={tag.id}>
            <TagLabel tag={tag} />
          </Grid>
        ))}
    </Grid>
  );
}
