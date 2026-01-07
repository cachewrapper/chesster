import axios from "axios";

const API_BASE_URL = "http://localhost:8080/api/v1";
class APIClient {
    constructor() {
        this.api = axios.create({
            baseURL: API_BASE_URL,
            headers: { "Content-Type": "application/json" },
            withCredentials: true,
            timeout: 10000
        });

        this.refreshPromise = null;
        this.api.interceptors.response.use(
            response => response,
            async error => this.handleResponseError(error)
        );
    }

    async checkAuth() {
        try {
            const response = await this.api.get("/auth/token/validate");
            return response.status === 202;
        } catch {
            return false;
        }
    }
    async handleResponseError(error) {
        const originalRequest = error.config;

        if (!originalRequest || originalRequest._retry || !error.response) {
            return Promise.reject(error);
        }

        if (error.response.status !== 401 || originalRequest.url?.includes("/auth/token/refresh")) {
            return Promise.reject(error);
        }

        originalRequest._retry = true;

        if (!this.refreshPromise) {
            this.refreshPromise = this.api
                .post("/auth/token/refresh")
                .then(response => {
                    this.refreshPromise = null;
                    return response;
                })
                .catch(err => {
                    this.refreshPromise = null;
                    throw err;
                });
        }

        try {
            await this.refreshPromise;
            return this.api(originalRequest);
        } catch {
            return Promise.reject(error);
        }
    }

    get(url, config = {}) {
        return this.api.get(url, config);
    }

    post(url, data, config = {}) {
        return this.api.post(url, data, config);
    }

    put(url, data, config = {}) {
        return this.api.put(url, data, config);
    }

    delete(url, config = {}) {
        return this.api.delete(url, config);
    }
}

const Api = new APIClient();
export default Api;
