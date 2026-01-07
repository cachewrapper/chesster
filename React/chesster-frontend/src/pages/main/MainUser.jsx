import Header from "../../components/Header";
import MainContent from "../../components/MainContent";
import { useEffect, useState } from "react";
import Api from "../../api/Api";

export default function MainUser() {
    const [username, setUsername] = useState("");

    useEffect(() => {
        const fetchProfile = async () => {
            try {
                const response = await Api.get("/users/profile");
                setUsername(response.data.username);
            } catch (err) {
                console.error("Failed to fetch profile", err);
            }
        };
        fetchProfile();
    }, []);

    return (
        <div className="flex flex-col min-h-screen">
            <Header />
            <MainContent username={username} />
        </div>
    );
}
