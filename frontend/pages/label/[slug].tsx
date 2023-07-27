import { useRouter } from 'next/router'
import { useEffect, useState } from 'react'
import { Icon, Paper, Stack, Typography } from '@mui/material'
import LocalOfferIcon from '@mui/icons-material/LocalOffer';
import axios from 'axios'
import Label from '../../interfaces/Label'
import Gallery from '../../components/Gallery';
import Submission from '../../interfaces/Submission';
export default function Page() {
    const [label, setLabel] = useState<Label>();
    const [submissions, setSubmissions] = useState<Submission[]>([]);
    const router = useRouter()
    useEffect(() => {
        const slug = router.query.slug;
        if (slug) {

            axios.get(`http://localhost:3001/labels/${router.query.slug}`, {
                headers: {
                    'Access-Control-Allow-Origin': '*',
                    'Content-Type': 'application/json',
                },

            }).then((response) => {
                setLabel(response.data);
            }).catch((error) => {
                console.log(error);
            });

            axios.get('http://localhost:3001/submissions/', {
                headers: {
                    'Access-Control-Allow-Origin': '*',
                    'Content-Type': 'application/json',
                }, params: {
                    labels: [router.query.slug]
                }
            }).then((response) => {
                setSubmissions(response.data);
            }).catch((error) => {
                console.log(error);
            });
        }


    }, [router.query.slug]);

    return (
        <Paper>
            <Stack spacing={2} direction="row">
                <Typography variant="h1">
                    <LocalOfferIcon />
                    {label?.name}
                </Typography>
            </Stack>
            <Gallery submissions={submissions} />
        </Paper>
    )

}