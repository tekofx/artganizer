import { useContext, Dispatch, SetStateAction } from "react";
import {
  Paper,
  Typography,
  TextField,
  Chip,
  Autocomplete,
} from "@mui/material";
import { DataContext } from "../../pages/_app";
import Tag from "../../interfaces/Tag";
interface TagSelectProps {
  setSelectedTags: Dispatch<SetStateAction<Tag[]>>;
  selectedTags: Tag[];
}
export default function TagSelect(props: TagSelectProps) {
  const { data } = useContext(DataContext);

  return (
    <Paper>
      <Typography>Tag select</Typography>

      <Autocomplete
        multiple
        id="tags-standard"
        options={data.tags}
        getOptionLabel={(option) => option.name}
        value={props.selectedTags}
        onChange={(event, value) => props.setSelectedTags(value)}
        renderInput={(params) => (
          <TextField
            {...params}
            variant="standard"
            label="Search Tags"
            placeholder="Favorites"
          />
        )}
        renderTags={(value, getTagProps) =>
          value.map((option, index) => (
            // eslint-disable-next-line react/jsx-key
            <Chip
              variant="outlined"
              label={option.name}
              style={{ backgroundColor: option.color }}
              {...getTagProps({ index })}
            />
          ))
        }
      />
    </Paper>
  );
}
