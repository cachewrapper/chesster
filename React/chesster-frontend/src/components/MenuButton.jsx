export default function MenuButton({ onClick, children, color = "gray" }) {
    const colorClasses = {
        gray: "bg-gray-800 hover:bg-gray-900",
        blue: "bg-blue-600 hover:bg-blue-700",
        red: "bg-red-600 hover:bg-red-700",
        green: "bg-green-600 hover:bg-green-700",
    };

    return (
        <button
            onClick={onClick}
            className={`px-6 py-3 rounded text-white transition ${colorClasses[color]}`}
        >
            {children}
        </button>
    );
}