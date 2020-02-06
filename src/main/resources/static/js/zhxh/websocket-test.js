const socket = Ext.create('app.ux.WebSocket',{
    url:'ws/kanban/line',
    reconnectTime:10,
    onConnected:function(){

    },
    onError:function (error) {

    },
    onClose:function () {

    },
    onMessage:function (message) {

    },
});

socket.open();