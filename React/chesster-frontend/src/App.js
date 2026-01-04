import { useContext } from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import MainGuest from "./pages/main/MainGuest";
import MainUser from "./pages/main/MainUser";
import { Login } from "./pages/Login";
import Register from "./pages/Register";
import { AuthContext } from "./context/AuthContext";

export default function App() {
    const { isLoggedIn, loading } = useContext(AuthContext);
    if (loading) return <div>Загрузка...</div>;

    console.log("isLoggedIn:", isLoggedIn);
    return (
        <Routes>
            <Route path="/" element={isLoggedIn ? <MainUser /> : <MainGuest />} />
            <Route path="/login" element={isLoggedIn ? <Navigate to="/" /> : <Login />} />
            <Route path="/register" element={isLoggedIn ? <Navigate to="/" /> : <Register />} />
        </Routes>
    );
}