package com.zhxh.imms.kanban;

import com.zhxh.imms.web.WebSocket;
import com.zhxh.imms.web.WebSocketMessage;
import org.springframework.stereotype.Component;

import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/ws/imms/kanban/progress")
@Component
public class ProgressKanban extends WebSocket {
    @Override
    protected void internalOnOpen() {
        //注册
        super.internalOnOpen();
    }

    @Override
    protected void internalOnClose() {
        //注销
        super.internalOnClose();
    }

    @Override
    protected void internalOnMessage(WebSocketMessage message) {
        //获取的是进度数据
    }

    @Override
    protected String toJson(WebSocketMessage message) {
        return null;
    }

    @Override
    protected WebSocketMessage fromJson(String strMessage) {
        return null;
    }

    @Override
    protected Object[] getSocketsToBroadcast(WebSocketMessage message) {
        //只返回LineKanban相关的WebSocket
        return super.getSocketsToBroadcast(message);
    }
}
