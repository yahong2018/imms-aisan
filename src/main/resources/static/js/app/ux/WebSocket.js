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

        this.callParent(arguments);
    },

    open: function () {
        try {
            const scheme = document.location.protocol === "https:" ? "wss" : "ws";
            const port = document.location.port ? (":" + document.location.port) : "";
            const wsUrl = scheme + "://" + document.location.hostname + port + "/" + this.url;
            const websocket = new WebSocket(wsUrl);

            this.websocket = websocket;
            const me = this;
            websocket.onopen = function () {
                console.log("websocket connected,before login...");
                if (me.onConnected) {
                    me.onConnected();
                }
            };
            websocket.onerror = function (evt) {
                console.log("web socked error");
                if (me.onError) {
                    me.onError(evt);
                }
            };
            websocket.onclose = function () {
                console.log("websocket closed.");
                if (me.onClose) {
                    me.onClose();
                }
                me.websocket = null;
                if (me.autoReconnect) {
                    console.log("websocket reconnect.");
                    me.reconnect();
                }
            };
            websocket.onMessage = function (evt) {
                console.log("websocket received message.");
                if (me.onMessage) {
                    me.onMessage(evt.data);
                }
            }
        } catch (e) {

        }
    },
    close: function () {
        this.websocket.close();
        this.websocket = null;
    },
    send: function (msg) {
        this.websocket.send(msg);
    },
    reconnect: function () {
        console.log("begin reconnect ...");
        setTimeout(this.open.apply(this), 1000 * this.reconnectTime);
    }
});