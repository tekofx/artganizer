import { useState, useContext, Dispatch, SetStateAction } from "react";
import {
  Paper,
  Typography,
  Button,
  TextField,
  Stack,
  FormControl,
  Select,
  Rating,
  Grid,
  MenuItem,
  SelectChangeEvent,
  OutlinedInput,
  InputLabel,
  ListItemText,
  Box,
  Chip,
} from "@mui/material";
import { DataContext } from "../pages/_app";
import Tag from "../interfaces/Tag";
import SubmissionForm from "../components/SubmissionForm";
export default function CreateSubmission() {
  const { data, setData } = useContext(DataContext);

  return (
    <Paper>
      <SubmissionForm />
    </Paper>
  );
}
