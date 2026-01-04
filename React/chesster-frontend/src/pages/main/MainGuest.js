import { Link } from "react-router-dom";

export default function MainGuest() {
    return (
        <main className="min-h-screen flex flex-col items-center justify-center bg-gray-50 font-sans">
            <h1 className="text-5xl font-bold mb-4">Chess Minimal</h1>
            <p className="text-gray-500 mb-12">Play chess, no distractions</p>
            <div className="flex flex-col sm:flex-row gap-6">
                <Link
                    to="/login"
                    className="px-8 py-4 bg-blue-600 text-white rounded shadow hover:bg-blue-700 transition"
                >
                    Войти
                </Link>
                <Link
                    to="/register"
                    className="px-8 py-4 bg-green-600 text-white rounded shadow hover:bg-green-700 transition"
                >
                    Регистрация
                </Link>
            </div>
        </main>
    );
}