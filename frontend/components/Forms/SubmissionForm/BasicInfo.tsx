import { Grid, Rating, Typography } from "@mui/material";
import { Dispatch, SetStateAction } from "react";

import Submission from "../../../interfaces/Submission";
import LimitedTextField from "../../LimitedTextField";

interface Props {
  submission: Submission;
  setSubmission: Dispatch<SetStateAction<Submission>>;
}

export default function BasicInfo({ submission, setSubmission }: Props) {
  return (
    <Grid container spacing={2}>
      <Grid item xs={12}>
        <Typography variant="h6">Basic Info</Typography>
        <LimitedTextField
          label="Title"
          fullWidth
          maxLength={50}
          value={submission.title}
          onChange={(event) => {
            setSubmission((prevSubmission) => ({
              ...prevSubmission,
              title: event.target.value,
            }));
          }}
        />
      </Grid>
      <Grid item xs={12}>
        <LimitedTextField
          label="Description"
          multiline
          maxLength={500}
          fullWidth
          value={submission.description}
          onChange={(event) => {
            setSubmission((prevSubmission) => ({
              ...prevSubmission,
              description: event.target.value,
            }));
          }}
        />
      </Grid>
      <Grid item xs={12}>
        <Typography variant="h6">Rating</Typography>
        <Rating
          value={submission.rating}
          onChange={(event, newValue) => {
            setSubmission((prevSubmission) => ({
              ...prevSubmission,
              rating: newValue || 0,
            }));
          }}
        />
      </Grid>
    </Grid>
  );
}
