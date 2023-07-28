import { useState, useEffect, useContext } from 'react';
import Container from '@mui/material/Container';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import Link from '../src/Link';
import axios from 'axios';
import Image from '../interfaces/Image';
import Gallery from '../components/Gallery';
import Submission from '../interfaces/Submission';
import { DataContext } from "../pages/_app";

export default function Home() {

  const [submissions, setSubmissions] = useState<Submission[]>([]);
  const { data, setData } = useContext(DataContext);


  return (
    <Container maxWidth={false}>
      <Box
        sx={{
          my: 4,
          display: 'flex',
          flexDirection: 'column',
          justifyContent: 'center',
          alignItems: 'center',
        }}
      >
        <Typography variant="h4" component="h1" gutterBottom>
          Welcome to the gallery
        </Typography>
        <Gallery submissions={data.submissions} />

      </Box>
    </Container>
  );
}
