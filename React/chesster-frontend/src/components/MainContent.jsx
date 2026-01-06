export default function MainContent({ username }) {
    return (
        <main className="flex-1 flex flex-col items-center justify-center bg-gray-50 p-6">
            <div className="max-w-2xl w-full text-center">
                <h2 className="text-3xl font-bold mb-4">
                    Добро пожаловать, {username || "Игрок"}!
                </h2>
                <p className="text-gray-500 mb-6">
                    Здесь вы можете сыграть в шахматы, управлять профилем и отслеживать
                    свой прогресс.
                </p>
                <div className="bg-white rounded-lg shadow p-6 flex flex-col items-center">
                    <img
                        src="/chess-banner.png"
                        alt="Chess Banner"
                        className="w-full h-48 object-cover rounded mb-4"
                    />
                    <p className="text-gray-700">
                        Начните партию прямо сейчас или изучите свою статистику!
                    </p>
                </div>
            </div>
        </main>
    );
}