Ext.define('app.store.BaseStore', {
    extend: 'Ext.data.Store',
    requires: ['app.ux.MapDataMeta'],
    mixins: ["app.store.StoreOperation"],

    constructor: function (config) {
        this.initUrlMeta(config);
        const proxyConfig = this.getDefaultProxy();
        proxyConfig.reader = {
            type: "json",
            rootProperty: 'rootProperty'
        };
        const configBase = {autoLoad: true, proxy: proxyConfig};
        Ext.applyIf(config, configBase);

        this.callParent(arguments);
    }
});