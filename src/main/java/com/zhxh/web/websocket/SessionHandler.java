package com.zhxh.web.websocket;

import javax.websocket.Session;

public interface SessionHandler {
    void onOpen(Session session);
    void onClose(Session session);
    void onError(Session session,Throwable error);
    void onMessage(String message, Session session);
}
