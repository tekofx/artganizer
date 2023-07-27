import { Roboto } from 'next/font/google';
import { createTheme } from '@mui/material/styles';
import { red } from '@mui/material/colors';

export const roboto = Roboto({
  weight: ['300', '400', '500', '700'],
  subsets: ['latin'],
  display: 'swap',
  fallback: ['Helvetica', 'Arial', 'sans-serif'],
});

declare module '@mui/material/styles' {
  interface TypographyVariants {
    image_title: React.CSSProperties;
  }

  // allow configuration using `createTheme`
  interface TypographyVariantsOptions {
    image_title?: React.CSSProperties;
  }
}
// Update the Typography's variant prop options
declare module '@mui/material/Typography' {
  interface TypographyPropsVariantOverrides {
    image_title: true;
  }
}
// Create a theme instance.
const theme = createTheme({
  palette: {
    mode: 'dark',
    background: {
      default: '#121212',
      paper: '#1E1E1E',
    },
    primary: {
      main: '#556cd6',
    },
    secondary: {
      main: '#19857b',
    },
    error: {
      main: red.A400,
    },
  },
  typography: {
    fontFamily: roboto.style.fontFamily,

  },
});


theme.typography.h1 = {
  fontSize: '2.5rem',
  fontWeight: 500,
  lineHeight: 1.2,
  letterSpacing: '-0.05rem',
  '@media (min-width:600px)': {
    fontSize: '3rem',
  },
  [theme.breakpoints.up('md')]: {
    fontSize: '4rem',
  },
};

theme.typography.image_title = {
  fontSize: '1rem',
  fontWeight: 500,
  letterSpacing: '-0.05rem',

  [theme.breakpoints.up('md')]: {
    fontSize: '1.2rem',
  },
};
export default theme;
