import { useState, useContext } from "react";
import {
    Paper, Typography, Button, TextField,
    Stack, FormControl, Select, Rating, Grid, MenuItem,
    SelectChangeEvent, OutlinedInput, InputLabel, ListItemText,
    Box, Chip
} from "@mui/material"
import Checkbox from '@mui/material/Checkbox';
import { Theme, useTheme } from '@mui/material/styles';
import { DataContext } from "../pages/_app"
import Tag from "../interfaces/Tag";

const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
    PaperProps: {
        style: {
            maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
            width: 250,
        },
    },
};

export default function CreateSubmission() {
    const { data, setData } = useContext(DataContext);

    const theme = useTheme();
    const [selectedTags, setSelectedTags] = useState<Tag[]>([]);

    const handleChange = (event: SelectChangeEvent<typeof selectedTags>) => {
        const {
            target: { value },
        } = event;
        if (typeof value === 'string') {
            // Handle string value (autofill mode)
            const selectedTagIds = value.split(',');
            const selectedTags = data.tags.filter(tag => selectedTagIds.includes(tag.id));
            setSelectedTags(selectedTags);
        } else {
            // Handle array value (normal mode)
            const selectedTagIds = value as string[];
            const selectedTags = data.tags.filter(tag => selectedTagIds.includes(tag.id));
            setSelectedTags(selectedTags);
        }
    };


    return (
        <Paper>
            <Typography>Create Submission</Typography>
            <Grid container>
                <Grid item xs={6}>
                    <FormControl fullWidth>
                        <Stack>
                            <TextField label="Title" />
                            <TextField label="Description" />
                            <Select
                                labelId="demo-multiple-chip-label"
                                id="demo-multiple-chip"
                                multiple
                                value={selectedTags}
                                onChange={handleChange}
                                input={<OutlinedInput id="select-multiple-chip" label="Chip" />}
                                renderValue={(selected) => (
                                    <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5 }}>
                                        {selected.map((tag) => (
                                            <Chip key={tag.id} label={tag.name} sx={{ backgroundColor: tag.color }} />
                                        ))}
                                    </Box>
                                )}
                                MenuProps={MenuProps}
                            >
                                {data.tags.map((tag) => (
                                    <MenuItem
                                        key={tag.id}
                                        value={tag.id}
                                    >
                                        {tag.name}
                                    </MenuItem>
                                ))}
                            </Select>
                            <Rating />
                            <TextField label="Rating" />
                            <TextField label="Image" />
                            <Button>Submit</Button>
                        </Stack>
                    </FormControl>
                </Grid>
                <Grid item xs={6}>
                    <img src="https://upload.wikimedia.org/wikipedia/commons/0/04/Labrador_Retriever_%281210559%29.jpg" width="100%" />

                </Grid>
            </Grid>
        </Paper>
    );
}
