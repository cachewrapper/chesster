import { useEffect, useState } from "react";
import API from "../api/Api";

export default function Profile() {
    const [profile, setProfile] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [uploading, setUploading] = useState(false);
    const [fileError, setFileError] = useState(null);

    useEffect(() => {
        const fetchProfile = async () => {
            try {
                const response = await API.get("/users/profile");
                setProfile(response.data);
            } catch (err) {
                console.error("Failed to fetch profile", err);
                setError("Не удалось загрузить профиль");
            } finally {
                setLoading(false);
            }
        };

        fetchProfile();
    }, []);

    if (loading) return <div>Загрузка профиля...</div>;
    if (error) return <div>{error}</div>;

    const avatarSrc = profile.avatar ? `data:image/png;base64,${profile.avatar}` : null;
    const handleFileChange = async (event) => {
        const file = event.target.files[0];
        setFileError(null);

        if (!file) return;

        if (file.size > 500_000) {
            setFileError("Файл слишком большой. Максимум 500 KB.");
            return;
        }

        const formData = new FormData();
        formData.append("file", file);

        try {
            setUploading(true);
            await API.put("/users/upload/avatar", formData, {
                headers: { "Content-Type": "multipart/form-data" },
            });

            const response = await API.get("/users/profile");
            setProfile(response.data);
        } catch (err) {
            console.error("Ошибка загрузки аватарки", err);
            setFileError("Не удалось загрузить аватарку");
        } finally {
            setUploading(false);
        }
    };

    return (
        <main className="min-h-screen flex flex-col items-center justify-center bg-gray-50 font-sans">
            <h1 className="text-4xl font-bold mb-6">Профиль</h1>

            <div className="bg-white p-6 rounded shadow w-80 text-gray-700 flex flex-col items-center">
                {avatarSrc ? (
                    <img
                        src={avatarSrc}
                        alt="Avatar"
                        className="w-24 h-24 rounded-full object-cover mb-4"
                    />
                ) : (
                    <div className="w-24 h-24 rounded-full bg-cyan-400 flex items-center justify-center mb-4 text-white text-3xl font-bold">
                        {profile.username?.[0]?.toUpperCase() || "U"}
                    </div>
                )}

                <label className="mt-2 cursor-pointer bg-cyan-500 hover:bg-cyan-600 text-white px-4 py-2 rounded">
                    {uploading ? "Загрузка..." : "Сменить аватар"}
                    <input
                        type="file"
                        accept="image/*"
                        className="hidden"
                        onChange={handleFileChange}
                        disabled={uploading}
                    />
                </label>

                {fileError && <p className="text-red-500 text-sm mt-2">{fileError}</p>}

                <p className="mt-4">
                    <strong>Username:</strong> {profile.username}
                </p>
                <p>
                    <strong>Email:</strong> {profile.email}
                </p>
            </div>
        </main>
    );
}