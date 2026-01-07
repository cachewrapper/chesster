package org.cachewrapper.handler;

import org.cachewrapper.socket.payload.request.RequestPayload;
import org.cachewrapper.socket.payload.response.ResponsePayload;

public interface GameHandler<U extends ResponsePayload, T extends RequestPayload> {
    U handle(T requestPayload);
}