import { Autocomplete, Button, TextField, Typography } from "@mui/material";
import { Dispatch, SetStateAction, useState } from "react";
import Tag from "../../interfaces/Tag";
import { useAppContext } from "../../pages/_app";
import TagChip from "./TagChip";
import TagLabel from "./TagLabel";
interface TagListProps {
  selectedTags: Tag[];
  setSelectedTags: Dispatch<SetStateAction<Tag[]>>;
}

const colors = [
  "#7BDCB5",
  "#FCB900",
  "#FF6900",
  "#0693E3",
  "#8ED1FC",
  "#F78DA7",
  "#EB144C",
  "#ABB8C3",
  "#9900EF",
];

export default function TagAutocomplete({
  setSelectedTags,
  selectedTags,
}: TagListProps) {
  const { createTag, tags } = useAppContext();
  const [inputValue, setInputValue] = useState("");

  function handleDeleteTag(tag: Tag) {
    setSelectedTags(selectedTags.filter((t) => t.id !== tag.id));
  }

  async function onAddTag() {
    var randomColor = colors[Math.floor(Math.random() * colors.length)];
    var newTag: Tag = {
      name: inputValue,
      color: randomColor,
      id: -1,
      submissionCount: 0,
    };
    var result = await createTag(newTag);
    if (result) {
      setSelectedTags([...selectedTags, result]);
      setInputValue("");
    }
  }

  return (
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
        const isSelected = selectedTags.some(
          (selectedTag) => selectedTag.id === tag.id
        );
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
      noOptionsText={
        inputValue != "" ? (
          <Button onClick={onAddTag}>Create tag {inputValue}</Button>
        ) : (
          <Typography>Type a name to create a Tag</Typography>
        )
      }
    />
  );
}
