import { useContext } from "react";
import { Routes, Route, Navigate } from "react-router-dom";

import MainGuest from "./pages/main/MainGuest";
import MainUser from "./pages/main/MainUser";
import { Login } from "./pages/Login";
import { Register } from "./pages/Register";
import { AuthContext } from "./context/AuthContext";
import Profile from "./pages/Profile";
import ChessPage from "./pages/ChessPage";

export default function App() {
    const { isLoggedIn, loading } = useContext(AuthContext);
    if (loading) return <div>Загрузка...</div>;

    return (
        <Routes>
            <Route path="/" element={isLoggedIn ? <MainUser /> : <MainGuest />} />
            <Route path="/login" element={isLoggedIn ? <Navigate to="/" /> : <Login />} />
            <Route path="/register" element={isLoggedIn ? <Navigate to="/" /> : <Register />} />
            <Route path="/profile" element={isLoggedIn ? <Profile /> : <Navigate to="/login" />} />
            <Route path="/game" element={<ChessPage />} />
        </Routes>
    );
}