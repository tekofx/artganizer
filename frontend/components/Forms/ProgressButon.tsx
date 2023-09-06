import { Button, CircularProgress, Box } from "@mui/material";
import { green } from "@mui/material/colors";

interface Props {
  loading: boolean;
  onClick: () => void;
  text: string;
}

export default function ProgressButton({ loading, onClick, text }: Props) {
  const buttonSx = {
    ...(loading && {
      bgcolor: green[500],
      "&:hover": {
        bgcolor: green[700],
      },
    }),
  };

  return (
    <Box sx={{ m: 1, position: "relative" }}>
      <Button
        variant="contained"
        disabled={loading}
        sx={buttonSx}
        onClick={() => onClick()}
      >
        {text}
      </Button>
      {loading && (
        <CircularProgress
          size={24}
          sx={{
            color: green[500],
            position: "absolute",
            top: "50%",
            left: "50%",
            marginTop: "-12px",
            marginLeft: "-12px",
          }}
        />
      )}
    </Box>
  );
}
