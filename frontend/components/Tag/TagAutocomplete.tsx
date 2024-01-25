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

const colors = ["#7BDCB5", "#FCB900", "#FF6900", "#0693E3", "#8ED1FC", "#F78DA7", "#EB144C", "#ABB8C3", "#9900EF"]

export default function TagAutocomplete({
  setSelectedTags,
  selectedTags,
}: TagListProps) {
  const [inputValue, setInputValue] = useState("");
  const [tags, setTags] = useState<Tag[]>([]);
  const getTags = async () => {
    var res = await axios.get(process.env.API_URL + "/tags");
    setTags(res.data);
  };
  useEffect(() => {
    getTags();
  }, []);

  function handleDeleteTag(tag: Tag) {
    setSelectedTags(selectedTags.filter((t) => t.id !== tag.id));
  }

  async function onAddTag() {
    var randomColor = colors[Math.floor(Math.random() * colors.length)];
    await axios.post(process.env.API_URL + `/tags`, { name: inputValue, color: randomColor }).then((res) => {
      setSelectedTags([...selectedTags, res.data]);
      setInputValue("");
      getTags();
    }
    );
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
          const isSelected = selectedTags.some((selectedTag) => selectedTag.id === tag.id);
          if (isSelected) {
            props["aria-selected"] = true;
          }
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
        noOptionsText={<Button onClick={onAddTag}>Create tag {inputValue}</Button>}
      />
    </>
  );
}
