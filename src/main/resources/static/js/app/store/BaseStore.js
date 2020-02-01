Ext.define('app.store.BaseStore', {
    extend: 'Ext.data.Store',
    requires: ['app.ux.MapDataMeta'],
    mixins: ["app.store.StoreOperation"],

    constructor: function (config) {
        this.initUrlMeta();

        var proxyConfig = {
            type: 'ajax',
            url: this.getSelectUrl(),
            headers: app.ux.Utils.getAuthorizeHeader(),
            exception: function (self, response, operation, eOpts) {
                if (response.status == 403) {
                    Ext.Msg.alert("系统提示", "系统需要重新登录！");

                    sessionStorage.removeItem("authentication_data");
                    window.location.href = "front-login";
                    return;
                }else{
                    Ext.Msg.alert("系统错误",response.responseText);
                }
            },
            reader: {
                type: "json",
                rootProperty: 'rootProperty'
            }
        };

        var configBase = { autoLoad: true, proxy: proxyConfig };
        Ext.applyIf(config, configBase);

        this.callParent(arguments);
    }
});