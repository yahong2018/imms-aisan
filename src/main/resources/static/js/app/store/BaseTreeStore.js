Ext.define('app.store.BaseTreeStore', {
    extend: 'Ext.data.TreeStore',
    requires: ['app.ux.MapDataMeta'],
    alias: "store.baseTreeStore",
    mixins: ["app.store.StoreOperation"],

    constructor: function (config) {
        this.initUrlMeta(config);

        var proxyConfig = this.getDefaultProxy();
        var configBase = {autoLoad: true, proxy: proxyConfig};
        Ext.applyIf(config, configBase);

        this.callParent(arguments);
    }
});