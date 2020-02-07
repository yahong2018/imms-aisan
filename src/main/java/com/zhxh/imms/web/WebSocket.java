package com.zhxh.imms.web;

import com.zhxh.imms.utils.Logger;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.*;

public abstract class WebSocket {
    private static volatile int onlineCount = 0;
    private static CopyOnWriteArraySet<WebSocket> socketList = new CopyOnWriteArraySet<>();
    private Session session;

    public Session getSession() {
        return session;
    }

    @OnOpen
    public final synchronized void onOpen(Session session) {
        Logger.info(WebSocket.getRemoteAddress(session) + "已连接服务器");
        socketList.add(this);
        this.session = session;
        addOnlineCount();

        this.internalOnOpen();
    }

    @OnClose
    public final synchronized void onClose(Session closeSession) {
        Logger.info(WebSocket.getRemoteAddress(closeSession) + "已从服务器断开");
        this.internalOnClose();

        socketList.remove(this);
        this.session = null;
        subOnlineCount();
    }

    @OnMessage
    public final void onMessage(String strMessage, Session session) {
        Logger.info(WebSocket.getRemoteAddress(session) + "收到消息:" + strMessage);
        try {
            WebSocketMessage message = this.fromJson(strMessage);
            this.internalOnMessage(message);
        } catch (Exception e) {
            Logger.error(e);
        }
    }

    @OnError
    public final void onError(Session session, Throwable error) {
        this.internalOnError(error);
    }

    protected void internalOnMessage(WebSocketMessage message) {
    }

    protected void internalOnOpen() {
    }

    protected void internalOnClose() {
    }

    protected void internalOnError(Throwable error) {
    }

    protected Object[] getSocketsToBroadcast(WebSocketMessage message) {
        return socketList.toArray();
    }

    protected abstract String toJson(WebSocketMessage message);
    protected abstract WebSocketMessage fromJson(String strMessage);

    public synchronized final void broadCast(WebSocketMessage message) {
        Object[] socketList = this.getSocketsToBroadcast(message);
        for (Object socket : socketList) {
            ((WebSocket) socket).sendMessage(message);
        }
    }

    public void sendMessage(String message) {
        if (this.session.isOpen()) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                Logger.error(e);
            }
        }
    }

    public void sendMessage(WebSocketMessage message) {
        String strMessage = this.toJson(message);
        if (StringUtils.isEmpty(strMessage)) {
            sendMessage(strMessage);
        }
    }

    public synchronized static int getOnlineCount() {
        return onlineCount;
    }

    public synchronized static void addOnlineCount() {
        onlineCount++;
    }

    public synchronized static void subOnlineCount() {
        onlineCount--;
    }

    protected static CopyOnWriteArraySet<WebSocket> getWebSocketSet() {
        return socketList;
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
               // Logger.error(e);
            }
        }

        return null;
    }
}
