import React from "react";
import { Grid } from "@mui/material";
import StarIcon from "@mui/icons-material/Star";
import StarBorderIcon from "@mui/icons-material/StarBorder";
interface RatingProps {
  value: number;
}

export default function Rating2(props: RatingProps) {
  return (
    <Grid container>
      {[...Array(5)].map((_, i) => (
        <Grid item key={i}>
          {props.value >= i + 1 ? <StarIcon /> : <StarBorderIcon />}
        </Grid>
      ))}
    </Grid>
  );
}
