import { Roboto } from "next/font/google";
import { createTheme } from "@mui/material/styles";
import { red } from "@mui/material/colors";

export const roboto = Roboto({
  weight: ["300", "400", "500", "700"],
  subsets: ["latin"],
  display: "swap",
  fallback: ["Helvetica", "Arial", "sans-serif"],
});

// Create a theme instance.
const theme = createTheme({
  palette: {
    mode: "dark",
    background: {
      default: "#121212",
      paper: "#1E1E1E",
    },
    primary: {
      main: "#556cd6",
    },
    secondary: {
      main: "#19857b",
    },
    error: {
      main: red.A400,
    },
  },
  typography: {
    fontFamily: roboto.style.fontFamily,
  },
  components: {
    MuiAccordionSummary: {
      styleOverrides: {
        root: {
          backgroundColor: "#1E1E1E",
          color: "#fff",
          flexDirection: "row-reverse",
          justifyContent: "space-between",
          height: "10",
          "&.Mui-expanded": {
            backgroundColor: "#1E1E1E",
            height: 10,
          },
        },
      },
    },
  },
});

theme.typography.h1 = {
  fontSize: "2.5rem",
  fontWeight: 500,
  lineHeight: 1.2,
  letterSpacing: "-0.05rem",
  "@media (min-width:600px)": {
    fontSize: "3rem",
  },
  [theme.breakpoints.up("md")]: {
    fontSize: "4rem",
  },
};

theme.typography.h2 = {
  fontSize: "2rem",
  [theme.breakpoints.up("md")]: {
    fontSize: "2.5rem",
  },
};

theme.typography.h3 = {
  fontSize: "1.5rem",
  [theme.breakpoints.up("md")]: {
    fontSize: "2rem",
  },
};

theme.typography.image_title = {
  fontSize: "1rem",
  fontWeight: 500,
  letterSpacing: "-0.05rem",

  [theme.breakpoints.up("md")]: {
    fontSize: "1.2rem",
  },
};

export default theme;
