import { Grid, Typography, Tooltip, Grow } from "@mui/material";

interface ColorPaletteProps {
  colors?: string[];
}
export default function ColorPalette(props: ColorPaletteProps) {
  return (
    <>
      <Grid container>
        {props.colors?.map((color) => (
          <Grid item xs key={color}>
            <Tooltip
              TransitionComponent={Grow}
              title={
                <Typography textAlign="center" style={{ color: "white" }}>
                  {color}
                </Typography>
              }
              placement="top"
              arrow
              sx={{ width: "200%" }}
            >
              <div
                style={{
                  height: "50%",
                  width: "100%",
                  backgroundColor: color,
                  paddingBottom: "50%",
                  cursor: "pointer",
                }}
              />
            </Tooltip>
          </Grid>
        ))}
      </Grid>
    </>
  );
}
