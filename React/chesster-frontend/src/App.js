import {useContext, useEffect} from "react";
import {Routes, Route, Navigate} from "react-router-dom";

import MainGuest from "./pages/main/MainGuest";
import MainUser from "./pages/main/MainUser";
import {Login} from "./pages/Login";
import {Register} from "./pages/Register";
import {AuthContext} from "./context/AuthContext";
import Profile from "./pages/Profile";
import ChessPage from "./pages/ChessPage";
import {GameWebSocket} from "./socket/GameWebSocket";

export default function App() {
    const {isLoggedIn} = useContext(AuthContext);

    useEffect(() => {
        const gameWebSocket = new GameWebSocket("ws://localhost:8083/ws");
        gameWebSocket.connect();

        gameWebSocket.onOpen(() => {
            gameWebSocket.sendMessage("Hello from App component!");
        })

        gameWebSocket.onMessage((msg) => {
            console.log("Received message: ", msg);
        })
    }, []);

    return (
        <Routes>
            <Route path="/" element={isLoggedIn ? <MainUser/> : <MainGuest/>}/>
            <Route path="/login" element={isLoggedIn ? <Navigate to="/"/> : <Login/>}/>
            <Route path="/register" element={isLoggedIn ? <Navigate to="/"/> : <Register/>}/>
            <Route path="/profile" element={isLoggedIn ? <Profile/> : <Navigate to="/login"/>}/>
            <Route path="/game" element={<ChessPage/>}/>
        </Routes>
    );
}