import axios from "axios";

const API_BASE_URL = "http://100.79.251.72:8080/api/v1";

const API = axios.create({
    baseURL: API_BASE_URL,
    headers: { "Content-Type": "application/json" },
    withCredentials: true
});

let refreshPromise = null;

export async function checkAuth() {
    try {
        const response = await API.get("/auth/token/validate");
        return response.status === 202;
    } catch {
        return false;
    }
}

API.interceptors.response.use(
    response => response,
    async error => {
        const originalRequest = error.config;

        if (
            !originalRequest ||
            originalRequest._retry ||
            error.response?.status !== 401 ||
            originalRequest.url?.includes("/auth/token/refresh")
        ) {
            return Promise.reject(error);
        }

        originalRequest._retry = true;

        if (!refreshPromise) {
            refreshPromise = API.post("/auth/token/refresh")
                .then(() => {
                    refreshPromise = null;
                })
                .catch(err => {
                    refreshPromise = null;
                    throw err;
                });
        }

        try {
            await refreshPromise;
            return API(originalRequest);
        } catch {
            return Promise.reject(error);
        }
    }
);

export default API;