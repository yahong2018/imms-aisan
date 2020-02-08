const socket = Ext.create('app.ux.WebSocket',{
    url:'ws/imms/kanban/progress',
    reconnectTime:10,
    onConnected:function(){
        this.send("factory kanban start ...");
    },
    onError:function (error) {

    },
    onClose:function () {

    },
    onMessage:function (message) {

    },
});
socket.open();

// var WebSocketProxy = {
//     open: function () {
//         try {
//             const scheme = document.location.protocol === "https:" ? "wss" : "ws";
//             const port = document.location.port ? (":" + document.location.port) : "";
//             const url = scheme + "://" + document.location.hostname + port + "/ws/imms/kanban/progress";
//             const websocket = new WebSocket(url);
//
//             WebSocketProxy.websocket = websocket;
//             websocket.onopen = function () {
//                 WebSocketProxy.waitTime = -1;
//                 console.log("connected,before login...");
//                 const signal = "factory kanban start ...";
//                 websocket.send(signal);
//                 console.log("after logined");
//             };
//             websocket.onerror = function (evt) {
//                 console.log("error");
//             };
//             websocket.onclose = function () {
//                 console.log("closed.");
//                 WebSocketProxy.reconnect();
//             };
//             websocket.onmessage = function (evt) {
//                 try {
//                     console.log(evt.data);
//                 } catch (e) {
//                     console.error(e);
//                 }
//             };
//         } catch (e) {
//         }
//     },
//     reconnect: function () {
//         console.log("begin reconnect ...");
//         WebSocketProxy.websocket = null;
//         WebSocketProxy.waitTime = new Date().getTime();
//         setTimeout(this.open, 1000 * 10);
//     }
// };
// WebSocketProxy.waitTime = new Date().getTime();
// WebSocketProxy.open();
//
// setInterval(function () {
//     const now = new Date().getTime();
//     if (WebSocketProxy.waitTime == -1) {
//         return;
//     }
//     const result = now - WebSocketProxy.waitTime;
//     if (result >= 1000 * 10) {
//         if (WebSocketProxy.websocket) {
//             try {
//                 let socket = WebSocketProxy.websocket;
//                 WebSocketProxy.websocket = null;
//
//                 socket.close();
//                 socket = null;
//                 delete socket;
//             } catch (e) {
//             }
//         }
//     }
// }, 1000 * 15);