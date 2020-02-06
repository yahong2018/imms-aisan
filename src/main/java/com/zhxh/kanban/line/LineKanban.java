package com.zhxh.kanban.line;

import com.zhxh.web.WebSocket;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/ws/kanban/line")
@Component
public class LineKanban extends WebSocket {
    @Override
    protected void internalOnMessage(String message, Session session) {
    }
}
