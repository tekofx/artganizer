import { useContext, Dispatch, SetStateAction } from "react";
import { Typography, MenuItem, Button } from "@mui/material";
import { DataContext } from "../../pages/_app";
import Tag from "../../interfaces/Tag";
import TagLabel from "./TagLabel";
interface TagListProps {
  selectedTags: Tag[];
  setSelectedTags: Dispatch<SetStateAction<Tag[]>>;
  setOpen: Dispatch<SetStateAction<boolean>>;
  filter: string;
}

export default function SelectableTagList(props: TagListProps) {
  const { data } = useContext(DataContext);

  function handleClick(tag: Tag) {
    if (props.selectedTags.includes(tag)) {
      props.setSelectedTags(props.selectedTags.filter((t) => t.id !== tag.id));
      return;
    }

    props.setSelectedTags([...props.selectedTags, tag]);
  }

  function tagInSelectedTags(tag: Tag) {
    // Check by tag id
    return props.selectedTags.some((t) => t.id === tag.id);
  }

  return (
    <>
      {data.tags.filter((artist) =>
        artist.name.toLowerCase().includes(props.filter.toLowerCase())
      ).length == 0 && <Typography>No tags</Typography>}
      {data.tags

        .filter((removeTag) =>
          removeTag.name.toLowerCase().includes(props.filter.toLowerCase())
        )

        .map((removeTag) => (
          <MenuItem
            key={removeTag.id}
            selected={tagInSelectedTags(removeTag)}
            onClick={() => handleClick(removeTag)}
          >
            <TagLabel tag={removeTag} />
          </MenuItem>
        ))}
      <Button onClick={() => props.setOpen(false)}>Close</Button>
    </>
  );
}
