import axios from "axios";
import { getCookie } from "../utils/cookie";

const API_BASE_URL = "http://100.79.251.72:8080/api/v1";

const API = axios.create({
    baseURL: API_BASE_URL,
    headers: { "Content-Type": "application/json" },
    withCredentials: true
});

let isRefreshing = false;
let pendingRefresh = null;

export async function checkAuth() {
    try {
        const res = await API.get("/auth/token/validate");
        return res.status === 202;
    } catch (e) {
        console.warn("User not authenticated:", e.response?.status);
        return false;
    }
}

API.interceptors.response.use(
    response => response,
    async error => {
        if (error.response?.status !== 401) return Promise.reject(error);

        const refreshToken = getCookie("refresh_token");
        if (!refreshToken) return Promise.reject(error);

        if (isRefreshing) {
            await pendingRefresh;
            return Promise.reject(error);
        }

        isRefreshing = true;
        pendingRefresh = API.post("/auth/token/refresh")
            .catch(refreshError => {
                console.error("Refresh failed:", refreshError);
            })
            .finally(() => {
                isRefreshing = false;
                pendingRefresh = null;
            });

        await pendingRefresh;

        return Promise.reject(error);
    }
);

export default API;
