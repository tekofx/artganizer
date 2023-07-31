import { Checkbox, Rating, Button, Menu, MenuItem, Stack, Paper } from "@mui/material";
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { useState, MouseEvent, useContext } from "react";
import { DataContext } from "../pages/_app";
import Tag from "../interfaces/Tag";
function RatingFilter() {
    const [value, setValue] = useState<number | null>(2);
    const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
    const open = Boolean(anchorEl);
    const handleClick = (event: MouseEvent<HTMLButtonElement>) => {
        setAnchorEl(event.currentTarget);
    };
    const handleClose = () => {
        setAnchorEl(null);
    };

    return (
        <div>
            <Button
                id="basic-button"
                aria-controls={open ? 'basic-menu' : undefined}
                aria-haspopup="true"
                aria-expanded={open ? 'true' : undefined}
                onClick={handleClick}
                endIcon={<ExpandMoreIcon />}
            >
                Rating
            </Button>
            <Menu
                id="basic-menu"
                anchorEl={anchorEl}
                open={open}
                onClose={handleClose}
                MenuListProps={{
                    'aria-labelledby': 'basic-button',
                }}
            >
                <MenuItem onClick={handleClose}>
                    <Rating
                        name="simple-controlled"
                        value={value}
                        onChange={(event, newValue) => {
                            setValue(newValue);
                        }} />
                </MenuItem>
            </Menu>
        </div>
    );
}

function TagFilter() {
    const { data, setData } = useContext(DataContext);
    const [selectedTags, setSelectedTags] = useState<Tag[]>([]);

    const [value, setValue] = useState<number | null>(2);
    const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
    const open = Boolean(anchorEl);
    const handleClick = (event: MouseEvent<HTMLButtonElement>) => {
        setAnchorEl(event.currentTarget);
    };
    const handleClose = () => {
        setAnchorEl(null);
    };

    const handleCheckboxChange = (event: React.ChangeEvent<HTMLInputElement>, tag: Tag) => {
        if (event.target.checked) {
            setSelectedTags([...selectedTags, tag]);
        } else {
            setSelectedTags(selectedTags.filter((tag) => tag !== tag));
        }
    };


    return (

        <div>
            <Button
                id="basic-button"
                aria-controls={open ? 'basic-menu' : undefined}
                aria-haspopup="true"
                aria-expanded={open ? 'true' : undefined}
                onClick={handleClick}
                endIcon={<ExpandMoreIcon />}
            >
                Tags
            </Button>
            <Menu
                id="basic-menu"
                anchorEl={anchorEl}
                open={open}
                onClose={handleClose}
                MenuListProps={{
                    'aria-labelledby': 'basic-button',
                }}
            >
                {data.tags.map((tag) => (
                    <MenuItem>
                        <Checkbox
                            value={tag}
                            onChange={(event) => {
                                handleCheckboxChange(event, tag);
                            }}
                        />
                        {tag.name}
                    </MenuItem>
                ))}
            </Menu>
        </div>
    );
}



export default function Filters() {
    return (
        <Paper>

            <Stack direction="row">
                <RatingFilter />
                <TagFilter />
            </Stack>
        </Paper>
    );
}