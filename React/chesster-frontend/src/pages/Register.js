export default function Register() {
    return (
        <main className="min-h-screen flex flex-col items-center justify-center bg-gray-50">
            <h1 className="text-3xl font-bold mb-6">Регистрация</h1>
            <form className="flex flex-col gap-4 w-80">
                <input className="p-2 border rounded" placeholder="Имя" />
                <input className="p-2 border rounded" placeholder="Email" />
                <input type="password" className="p-2 border rounded" placeholder="Пароль" />
                <button className="p-2 bg-green-600 text-white rounded hover:bg-green-700 transition">
                    Зарегистрироваться
                </button>
            </form>
        </main>
    );
}