import { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import Api from "../api/Api";
import { AuthContext } from "../context/AuthContext";

export function Register() {
    const navigate = useNavigate();
    const { setIsLoggedIn } = useContext(AuthContext);

    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (event) => {
        event.preventDefault();
        setError("");

        if (!username || !email || !password) {
            setError("Все поля обязательны");
            return;
        }

        setLoading(true);
        try {
            await Api.post("/auth/register", {
                username: username.trim(),
                email: email.trim(),
                password: password.trim(),
            });

            setIsLoggedIn(true);
            navigate("/");
        } catch (err) {
            console.error(err);
            setError(
                err.response?.data?.message ||
                "Не удалось зарегистрироваться. Попробуйте позже."
            );
        } finally {
            setLoading(false);
        }
    };

    return (
        <main className="min-h-screen flex flex-col items-center justify-center bg-gray-50 p-4">
            <h1 className="text-3xl font-bold mb-6">Регистрация</h1>
            <form
                onSubmit={handleSubmit}
                className="flex flex-col gap-4 w-80 bg-white p-6 rounded shadow"
            >
                {error && <p className="text-red-600 text-sm">{error}</p>}

                <input
                    type="text"
                    placeholder="Имя"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    className="p-2 border rounded focus:outline-none focus:ring-2 focus:ring-cyan-400"
                    required
                />
                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    className="p-2 border rounded focus:outline-none focus:ring-2 focus:ring-cyan-400"
                    required
                />
                <input
                    type="password"
                    placeholder="Пароль"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    className="p-2 border rounded focus:outline-none focus:ring-2 focus:ring-cyan-400"
                    required
                />

                <button
                    type="submit"
                    disabled={loading}
                    className={`p-2 rounded text-white transition ${
                        loading ? "bg-gray-400 cursor-not-allowed" : "bg-green-600 hover:bg-green-700"
                    }`}
                >
                    {loading ? "Регистрация..." : "Зарегистрироваться"}
                </button>
            </form>
        </main>
    );
}