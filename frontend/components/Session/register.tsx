import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import { Alert } from '@mui/material';
import Avatar from '@mui/material/Avatar';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import axios from 'axios';
import { Dispatch, SetStateAction, useEffect, useState } from 'react';
import { AlertMessage } from '../../interfaces';

interface RegisterProps {
    setShowLogin: Dispatch<SetStateAction<boolean>>;
    setAlertMessage: Dispatch<SetStateAction<AlertMessage>>;
    setShowAlert: Dispatch<SetStateAction<boolean>>;
}

export default function Register({ setShowLogin, setAlertMessage, setShowAlert }: RegisterProps) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [passwordRepeat, setPasswordRepeat] = useState('');


    function hasLowerCaseAndUppercase(str: string) {
        const hasLowerCase = (str !== str.toLowerCase());
        const hasUpperCase = (str !== str.toUpperCase());

        return hasLowerCase && hasUpperCase;
    }

    function hasNumber(str: string) {
        return /\d/.test(str);
    }
    function hasSpecialChar(str: string) {
        return /[ `!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~]/.test(str);
    }

    useEffect(() => {

        if (password.length == 0) {
            setShowAlert(false);
            return;
        }

        if (password.length < 8) {
            setAlertMessage({ severity: 'error', message: 'Password must be at least 8 characters' });
            setShowAlert(true);
        } else if (!hasLowerCaseAndUppercase(password)) {
            setAlertMessage({ severity: 'error', message: 'Password must contain at least one uppercase letter' });
            setShowAlert(true);
        } else if (!hasNumber(password)) {
            setAlertMessage({ severity: 'error', message: 'Password must contain at least one number' });
            setShowAlert(true);
        } else if (!hasSpecialChar(password)) {
            setAlertMessage({ severity: 'error', message: 'Password must contain at least one special character' });
            setShowAlert(true);
        } else {
            setShowAlert(false);

        }
    }
        , [password]);


    async function handleSubmit() {
        if (password !== passwordRepeat) {
            console.log('Passwords do not match');
            setShowAlert(true);
            setAlertMessage({ severity: 'error', message: 'Passwords do not match' });
            return;
        }

        const data = { username: username, password: password };

        await axios.post('/api/register', data).then(response => {
            console.log(response.data);
            setShowLogin(true)
            setShowAlert(true);
            setAlertMessage({ severity: 'success', message: 'User created successfully' });

        }
        ).catch(error => {
            setAlertMessage({ severity: 'error', message: error.response.data.message });
            setShowAlert(true);
            console.log(error);
        });
    }


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
                Register
            </Typography>
            <Alert severity='info'>
                <Typography>You need to create a user to use Artganizer</Typography>
            </Alert>
            <Box component="form" noValidate sx={{ mt: 1 }}>
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    id="username"
                    label="Username"
                    name="username"
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
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    name="password-repeat"
                    label="Repeat Password"
                    type="password"
                    id="password-repeat"
                    autoComplete="current-password"
                    onChange={(e) => setPasswordRepeat(e.target.value)}
                />

                <Button
                    fullWidth
                    variant="contained"
                    sx={{ mt: 3, mb: 2 }}
                    onClick={handleSubmit}
                    disabled={username === '' || password === '' || passwordRepeat === ''}
                >
                    Register
                </Button>


            </Box>
        </Box>
    );
}