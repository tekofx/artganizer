import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Avatar from '@mui/material/Avatar';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import axios from 'axios';
import { AlertMessage } from 'interfaces';
import { useRouter } from 'next/router';
import * as React from 'react';
import Cookies from "universal-cookie";

interface RegisterProps {
    setAlertMessage: React.Dispatch<React.SetStateAction<AlertMessage>>;
    setShowAlert: React.Dispatch<React.SetStateAction<boolean>>;
}

export default function Login({ setAlertMessage, setShowAlert }: RegisterProps) {
    const [username, setUsername] = React.useState('');
    const [password, setPassword] = React.useState('');
    const router = useRouter();

    async function login() {
        await axios.post('/api/login', {
            username: username,
            password: password
        })
            .then((response) => {
                setShowAlert(false);
                var cookies = new Cookies();
                cookies.set("TOKEN", response.data.token, { path: "/" })
                router.push('/');
            })
            .catch((error) => {
                setAlertMessage({ severity: 'error', message: error.response.data });
                setShowAlert(true);
            });

    };

    return (
        <Box
            sx={{
                marginTop: 8,
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
            }}
        >
            <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
                <LockOutlinedIcon />
            </Avatar>
            <Typography component="h1" variant="h5">
                Sign in
            </Typography>
            <Box component="form" noValidate sx={{ mt: 1 }}>
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    id="username"
                    label="Username"
                    name="username"
                    autoComplete="username"
                    autoFocus
                    onChange={(e) => setUsername(e.target.value)}
                />
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    name="password"
                    label="Password"
                    type="password"
                    id="password"
                    autoComplete="current-password"
                    onChange={(e) => setPassword(e.target.value)}
                />
                <Button
                    fullWidth
                    variant="contained"
                    onClick={login}
                    sx={{ mt: 3, mb: 2 }}
                    disabled={username === '' || password === ''}

                >
                    Sign In
                </Button>

            </Box>
        </Box>
    );
}