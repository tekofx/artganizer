import { useState, useEffect } from 'react';
import Container from '@mui/material/Container';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import Link from '../src/Link';
import axios from 'axios';
import Image from '../interfaces/Image';
import Gallery from '../components/Gallery';
import Submission from '../interfaces/Submission';

export default function Home() {

  const [submissions, setSubmissions] = useState<Submission[]>([]);

  useEffect(() => {

    axios.get("http://localhost:3001/submissions", {
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Content-Type': 'application/json',
      },

    }).then((response) => {
      setSubmissions(response.data);
    }).catch((error) => {
      console.log(error);
    });


  }, []);
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
        <Gallery submissions={submissions} />
        <Link href="/about" color="secondary">
          Go to the about page
        </Link>
      </Box>
    </Container>
  );
}
