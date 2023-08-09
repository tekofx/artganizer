import {
  Stack,
  MenuItem,
  Typography,
  IconButton,
  Accordion,
  AccordionSummary,
  AccordionDetails,
  Icon,
  TextField,
} from "@mui/material";
import { useState, useContext } from "react";
import AddIcon from "@mui/icons-material/Add";
import FolderIcon from "@mui/icons-material/Folder";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import { useRouter } from "next/router";
import axios from "axios";
import { DataContext } from "../../pages/_app";

function FolderList() {
  const { data } = useContext(DataContext);
  const router = useRouter();

  return (
    <>
      {data.folders.length == 0 && <Typography>No folders</Typography>}
      {data.folders.map((folder) => (
        <MenuItem
          key={folder.id}
          onClick={() => router.push(`/folder/${folder.id}`)}
        >
          <Stack direction="row" spacing={1}>
            <Icon>
              <FolderIcon />
            </Icon>

            <Typography>{folder.name}</Typography>
          </Stack>
        </MenuItem>
      ))}
    </>
  );
}

export default function FolderAccordion() {
  const [expanded, setExpanded] = useState<boolean>(true);
  const [newFolder, setNewFolder] = useState<string>("Folder");
  const [showCreateFolder, setShowCreateFolder] = useState<boolean>(false);
  const [textFieldError, setTextFieldError] = useState<boolean>(false);

  const onTextFieldChange = (event: any) => {
    setNewFolder(event.target.value);
    if (event.target.value == "") {
      setTextFieldError(true);
    } else {
      setTextFieldError(false);
    }
  };

  async function createFolder() {
    await axios
      .post("http://localhost:3001/folders", {
        name: newFolder,
      })
      .then((response) => {
        data.folders = response.data;
        setData(data);
        setNewFolder("");
      })
      .catch((error) => {
        console.log(error);
      });
    setShowCreateFolder(false);
  }

  const { data, setData } = useContext(DataContext);

  return (
    <Accordion expanded={expanded}>
      <AccordionSummary
        expandIcon={<ExpandMoreIcon onClick={() => setExpanded(!expanded)} />}
        aria-controls="panel1a-content"
        id="panel1a-header"
      >
        <Stack direction="row" spacing={1} alignItems="center">
          <Typography onClick={() => setExpanded(!expanded)}>
            Folders
          </Typography>
          <IconButton onClick={() => setShowCreateFolder(true)}>
            <AddIcon />
          </IconButton>
        </Stack>
      </AccordionSummary>
      <AccordionDetails>
        <Stack direction="column">
          <FolderList />
          {showCreateFolder && (
            <>
              <Stack direction="row">
                <TextField
                  id="outlined-basic"
                  label="Folder name"
                  variant="outlined"
                  fullWidth={false}
                  error={textFieldError}
                  helperText={
                    textFieldError ? "Folder name cannot be empty" : ""
                  }
                  value={newFolder}
                  onChange={onTextFieldChange}
                />
                <IconButton onClick={createFolder}>
                  <AddIcon />
                </IconButton>
              </Stack>
            </>
          )}
        </Stack>
      </AccordionDetails>
    </Accordion>
  );
}
