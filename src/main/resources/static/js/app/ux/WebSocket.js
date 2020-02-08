Ext.define('app.ux.WebSocket', {
    constructor: function (config) {
        config = config || {};

        this.url = config.url;
        this.onConnected = config.onConnected;
        this.onError = config.onError;
        this.onClose = config.onClose;
        this.onMessage = config.onMessage;
        this.autoReconnect = config.autoReconnect;
        if (config.autoReconnect !== false) {
            this.autoReconnect = true;
        }
        this.reconnectTime = config.reconnectTime;
        if (this.reconnectTime == null) {
            this.reconnectTime = 10;
        }
        if (this.autoReconnect) {
            let me = this;
            setInterval(function () {
                const now = new Date().getTime();
                if (me.waitTime == -1) {
                    return;
                }
                const result = now - me.waitTime;
                if (result >= (me.reconnectTime * 1000)) {
                    console.log("connection timeout,closing the socket...")
                    let socket = me.websocket;
                    me.websocket = null;
                    if (socket) {
                        try {
                            socket.close();
                            socket = null;
                        } catch (e) {
                            console.error(e);
                        }
                    }
                }
            }, 1000 * me.reconnectTime * 1.5);
        }

        this.callParent(arguments);
    },

    open: function () {
        const me = this;
        try {
            console.log("start connect...");
            const scheme = document.location.protocol === "https:" ? "wss" : "ws";
            const port = document.location.port ? (":" + document.location.port) : "";
            const wsUrl = scheme + "://" + document.location.hostname + port + "/" + this.url;

            this.waitTime = new Date().getTime();
            const websocket = new WebSocket(wsUrl);
            this.websocket = websocket;
            websocket.onopen = function () {
                me.waitTime = -1;
                console.log("websocket connected");
                if (me.onConnected) {
                    me.onConnected.call(me);
                }
            };
            websocket.onerror = function (evt) {
                console.log("web socked error");
                if (me.onError) {
                    me.onError.call(me, evt);
                }
            };
            websocket.onclose = function () {
                console.log("websocket closed.");
                if (me.onClose) {
                    me.onClose.call(me);
                }
                me.websocket = null;
                if (me.autoReconnect) {
                    console.log("websocket reconnect.");
                    me.reconnect.call(me);
                }
            };
            websocket.onMessage = function (evt) {
                console.log("websocket received message.");
                if (me.onMessage) {
                    me.onMessage.call(me, evt.data);
                }
            }
        } catch (e) {
            log.error(e);
        }
    },
    close: function () {
        this.websocket.close();
        this.websocket = null;
    },
    send: function (msg) {
        console.log("sending to server:" + msg);
        this.websocket.send(msg);
        console.log("message sent");
    },
    reconnect: function () {
        console.log("ready to reconnect ...");
        const me = this;
        me.waitTime = new Date().getTime();
        setTimeout(function () {
            me.open.call(me);
        }, me.reconnectTime * 1000);
    }
});