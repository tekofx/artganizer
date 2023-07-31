import { Checkbox, Rating, Button, Menu, MenuItem, Stack, Paper } from "@mui/material";
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { useState, MouseEvent, useContext } from "react";
import { DataContext } from "../pages/_app";
import Tag from "../interfaces/Tag";
function RatingFilter() {
    const { data, setData } = useContext(DataContext);
    const [value, setValue] = useState<number | null>(data.filters.rating);
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
                        value={data.filters.rating}
                        onChange={(event, newValue) => {
                            const newData = { ...data };
                            newData.filters.rating = newValue || -1;
                            console.log(`Rating filter: ${newValue}`);
                            setData(newData);
                        }} />
                </MenuItem>
            </Menu>
        </div>
    );
}

function TagFilter() {
    const { data, setData } = useContext(DataContext);

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
            const newData = { ...data };
            newData.filters.tags = [...data.filters.tags, tag];
            setData(newData);

            console.log("Tag filter: Added tag " + tag.name)


        } else {
            const newData = { ...data };
            newData.filters.tags = newData.filters.tags.filter(t => t.id != tag.id);
            setData(newData);
            console.log("Tag filter: Removed tag " + tag.name)
        }
        console.log("Tag filter " + data.filters)
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
                    <MenuItem key={tag.id}>
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