import { Paper, TextField, IconButton } from "@mui/material"
import SearchIcon from '@mui/icons-material/Search';
export default function Search() {
    return (
        <Paper>
            <IconButton>
                <SearchIcon />
            </IconButton>
            <TextField
                id="outlined-basic"
                label="Search"
                variant="outlined"
                size="small"
            />

        </Paper>
    )
}