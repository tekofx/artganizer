import { Dispatch, SetStateAction, useEffect, useState } from "react";
import { TextField, Autocomplete, Button } from "@mui/material";
import Tag from "../../interfaces/Tag";
import TagLabel from "./TagLabel";
import TagChip from "./TagChip";
import axios from "axios";
interface TagListProps {
  selectedTags: Tag[];
  setSelectedTags: Dispatch<SetStateAction<Tag[]>>;
}

export default function SelectableTagList({
  setSelectedTags,
  selectedTags,
}: TagListProps) {
  const [inputValue, setInputValue] = useState("");
  const [tags, setTags] = useState<Tag[]>([]);
  useEffect(() => {
    const getTags = async () => {
      var res = await axios.get(process.env.API_URL + "/tags");
      setTags(res.data);
    };
    getTags();
  }

    , []);

  function handleDeleteTag(tag: Tag) {
    setSelectedTags(selectedTags.filter((t) => t.id !== tag.id));
  }

  return (
    <>
      <Autocomplete
        multiple
        options={tags}
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
