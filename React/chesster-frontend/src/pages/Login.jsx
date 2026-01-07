import { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import Api from "../api/Api";
import { AuthContext } from "../context/AuthContext";

export function Login() {
    const navigate = useNavigate();
    const { setIsLoggedIn } = useContext(AuthContext);

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const handleSubmit = async (event) => {
        event.preventDefault();
        setError("");

        try {
            await Api.post("/auth/login", { email, password });
            setIsLoggedIn(true);
            navigate("/");
        } catch (error) {
            console.error(error);
            setError(error.response?.data?.message || "Ошибка входа");
        }
    };

    return (
        <main className="min-h-screen flex flex-col items-center justify-center bg-gray-50">
            <h1 className="text-3xl font-bold mb-6">Вход</h1>
            <form onSubmit={handleSubmit} className="flex flex-col gap-4 w-80">
                <input
                    type="email"
                    className="p-2 border rounded"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />
                <input
                    type="password"
                    className="p-2 border rounded"
                    placeholder="Пароль"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                <button
                    type="submit"
                    className="p-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition"
                >
                    Войти
                </button>
                {error && <p className="text-red-600 mt-2">{error}</p>}
            </form>
        </main>
    );
}