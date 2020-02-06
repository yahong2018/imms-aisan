package com.zhxh.web.websocket;

import com.zhxh.utils.Logger;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.*;

public class WebSocket {
    private volatile int onlineCount = 0;
    private static CopyOnWriteArraySet<Session> sessionSet = new CopyOnWriteArraySet<>();
    private SessionHandler handler;

    @OnOpen
    public void onOpen(Session session) {
        Logger.info(WebSocket.getRemoteAddress(session) + "已连接服务器");
        sessionSet.add(session);
        addOnlineCount();
        handler.onOpen(session);
    }

    @OnClose
    public void onClose(Session closeSession) {
        Logger.info(WebSocket.getRemoteAddress(closeSession) + "已从服务器断开");
        sessionSet.remove(closeSession);
        subOnlineCount();
        handler.onOpen(closeSession);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        handler.onMessage(message, session);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        handler.onError(session, error);
    }

    public synchronized static void broadCast(String message) {
        for (Session session : sessionSet) {
            sendMessage(session, message);
        }
    }

    public static void sendMessage(Session session, String message) {
        try {
            if (session.isOpen()) {
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

    protected CopyOnWriteArraySet<Session> getWebSocketSet() {
        return sessionSet;
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
                Logger.debug(e);
            }
        }

        return null;
    }

    public SessionHandler getHandler() {
        return handler;
    }

    public void setHandler(SessionHandler handler) {
        this.handler = handler;
    }
}
