import MenuButton from "./MenuButton";
import Api from "../api/Api";
import { useNavigate } from "react-router-dom";

export default function Header() {
    const navigate = useNavigate();

    const handleLogout = async () => {
        try {
            await Api.post("/auth/logout", {});
            window.location.reload();
        } catch (err) {
            console.error("Logout failed", err);
        }
    };

    return (
        <header className="w-full bg-white shadow-md flex justify-between items-center px-6 py-3">
            <h1 className="text-xl font-bold text-gray-800">ChessApp</h1>
            <div className="flex gap-4">
                <MenuButton onClick={() => navigate("/game")} color="green">
                    Играть
                </MenuButton>
                <MenuButton onClick={() => navigate("/profile")} color="blue">
                    Профиль
                </MenuButton>
                <MenuButton onClick={handleLogout} color="red">
                    Выйти
                </MenuButton>
            </div>
        </header>
    );
}
