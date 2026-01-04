import API from "../../api/Api";

export default function MainUser() {
    const handleLogout = async () => {
        try {
            await API.post("/auth/logout", {});
            window.location.href = "/";
        } catch (exception) {
            console.error("Logout failed", exception);
        }
    };

    return (
        <main className="min-h-screen flex flex-col items-center justify-center bg-gray-50 font-sans">
            <h1 className="text-4xl font-bold mb-6">Добро пожаловать!</h1>
            <p className="text-gray-500 mb-12">Вы авторизованы, можете играть в шахматы</p>

            <div className="flex gap-6">
                <button className="px-6 py-3 bg-gray-800 text-white rounded hover:bg-gray-900 transition">
                    Играть
                </button>

                <button
                    onClick={handleLogout}
                    className="px-6 py-3 bg-red-600 text-white rounded hover:bg-red-700 transition"
                >
                    Выйти
                </button>
            </div>
        </main>
    );
}
