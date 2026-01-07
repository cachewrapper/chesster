export class GameWebSocket {
    constructor(url) {
        this.url = url;
        this.webSocket = null;
        this.isConnected = false;
        this.messageCallbacks = [];
        this.openCallbacks = [];
        this.closeCallbacks = [];
        this.errorCallbacks = [];
    }

    connect() {
        this.webSocket = new WebSocket(this.url);

        this.webSocket.onopen = () => {
            this.isConnected = true;
            console.log("WebSocket connected");
            this.openCallbacks.forEach(cb => cb());
        };

        this.webSocket.onmessage = (event) => {
            this.messageCallbacks.forEach(cb => cb(event.data));
        };

        this.webSocket.onclose = () => {
            this.isConnected = false;
            console.log("WebSocket closed");
            this.closeCallbacks.forEach(cb => cb());
        };

        this.webSocket.onerror = (error) => {
            console.error("WebSocket error:", error);
            this.errorCallbacks.forEach(cb => cb(error));
        };
    }

    sendMessage(message) {
        if (this.isConnected) {
            this.webSocket.send(message);
        } else {
            console.warn("WebSocket not connected yet!");
        }
    }

    onMessage(callback) {
        this.messageCallbacks.push(callback);
    }

    onOpen(callback) {
        this.openCallbacks.push(callback);
    }

    onClose(callback) {
        this.closeCallbacks.push(callback);
    }

    onError(callback) {
        this.errorCallbacks.push(callback);
    }

    disconnect() {
        if (this.webSocket) {
            this.webSocket.close();
        }
    }
}