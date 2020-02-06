package com.zhxh.web;

import com.zhxh.utils.Logger;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.*;

public abstract class WebSocket {
    private static volatile int onlineCount = 0;
    private static CopyOnWriteArraySet<WebSocket> socketList = new CopyOnWriteArraySet<>();
    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        Logger.info(WebSocket.getRemoteAddress(session) + "已连接服务器");
        socketList.add(this);
        this.session = session;
        addOnlineCount();
    }

    @OnClose
    public void onClose(Session closeSession) {
        Logger.info(WebSocket.getRemoteAddress(closeSession) + "已从服务器断开");
        socketList.remove(this);
        subOnlineCount();
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        Logger.info(WebSocket.getRemoteAddress(session) + "收到消息:" + message);
        this.internalOnMessage(message, session);
    }

    @OnError
    public void onError(Session session, Throwable error) {
    }

    protected abstract void internalOnMessage(String message, Session session);

    public synchronized void broadCast(String message) {
        for (WebSocket socket : socketList) {
            sendMessage(socket.session, message);
        }
    }

    public void sendMessage(Session session, String message) {
        try {
            if (session.isOpen()) {
                Logger.info(WebSocket.getRemoteAddress(session) + "发送消息:" + message);
                session.getBasicRemote().sendText(message);
            }
        } catch (IOException e) {
            Logger.error(e);
        }
    }

    public synchronized int getOnlineCount() {
        return onlineCount;
    }

    public synchronized void addOnlineCount() {
        onlineCount++;
    }

    public synchronized void subOnlineCount() {
        onlineCount--;
    }

    protected CopyOnWriteArraySet<WebSocket> getWebSocketSet() {
        return socketList;
    }

    public Session getSession() {
        return session;
    }

    public static InetSocketAddress getRemoteAddress(Session session) {
        if (session == null) {
            return null;
        }
        RemoteEndpoint.Async async = session.getAsyncRemote();
        //在Tomcat 8.0.x版本有效
//		InetSocketAddress addr = (InetSocketAddress) getFieldInstance(async,"base#sos#socketWrapper#socket#sc#remoteAddress");
        //在Tomcat 8.5以上版本有效
        InetSocketAddress addr = (InetSocketAddress) getFieldInstance(async, "base#socketWrapper#socket#sc#remoteAddress");
        return addr;
    }

    private static Object getFieldInstance(Object obj, String fieldPath) {
        String fields[] = fieldPath.split("#");
        for (String field : fields) {
            obj = getField(obj, obj.getClass(), field);
            if (obj == null) {
                return null;
            }
        }

        return obj;
    }

    private static Object getField(Object obj, Class<?> clazz, String fieldName) {
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Field field;
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(obj);
            } catch (Exception e) {
                Logger.error(e);
            }
        }

        return null;
    }
}
