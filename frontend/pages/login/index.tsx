import { Alert, Container } from "@mui/material";
import axios from "axios";
import Login from "components/Session/login";
import Register from "components/Session/register";
import { AlertMessage } from "interfaces";
import { useEffect, useState } from "react";


export default function Page() {
    const [showLogin, setShowLogin] = useState(true);
    const [showAlert, setShowAlert] = useState(false);
    const [alertMessage, setAlertMessage] = useState<AlertMessage>({ severity: 'error', message: '' });

    useEffect(() => {
        const checkExistUsers = async () => {
            await axios.get("/api/register")
                .then(() => {
                    setShowLogin(true);
                }).catch(() => {
                    setShowLogin(false);
                }
                );
        }
        checkExistUsers();
    }
        , []);
    return (
        <Container component="main" maxWidth="xs">

            {showLogin ? <Login setAlertMessage={setAlertMessage} setShowAlert={setShowAlert} /> : <Register setShowLogin={setShowLogin} setAlertMessage={setAlertMessage} setShowAlert={setShowAlert} />}
            {showAlert && alertMessage.message !== '' ? <Alert severity={alertMessage.severity} sx={{ textAlign: "center" }}>{alertMessage.message}</Alert> : null}
        </Container>
    );
}

