import { useContext, Dispatch, SetStateAction, useState } from "react";
import { TextField, Autocomplete, Button } from "@mui/material";
import { DataContext } from "../../pages/_app";
import Tag from "../../interfaces/Tag";
import TagLabel from "./TagLabel";
import TagChip from "./TagChip";
interface TagListProps {
  selectedTags: Tag[];
  setSelectedTags: Dispatch<SetStateAction<Tag[]>>;
}

export default function SelectableTagList({
  setSelectedTags,
  selectedTags,
}: TagListProps) {
  const { data } = useContext(DataContext);
  const [inputValue, setInputValue] = useState("");

  function handleDeleteTag(tag: Tag) {
    setSelectedTags(selectedTags.filter((t) => t.id !== tag.id));
  }

  return (
    <>
      <Autocomplete
        multiple
        options={data.tags}
        getOptionLabel={(tag) => tag.name}
        value={selectedTags}
        inputValue={inputValue}
        onInputChange={(event, newInputValue) => {
          setInputValue(newInputValue);
        }}
        onChange={(event, newValue) => {
          setSelectedTags(newValue);
        }}
        renderInput={(params) => (
          <TextField {...params} variant="outlined" label="Tags" />
        )}
        renderOption={(props, tag) => {
          return (
            <li {...props}>
              <TagLabel tag={tag} />
            </li>
          );
        }}
        renderTags={(value) =>
          value.map((tag) => (
            <TagChip
              key={tag.id}
              tag={tag}
              onDelete={() => handleDeleteTag(tag)}
            />
          ))
        }
        noOptionsText={<Button>Create tag {inputValue}</Button>}
      />
    </>
  );
}
