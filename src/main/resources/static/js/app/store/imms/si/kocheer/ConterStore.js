Ext.define("app.store.imms.si.kocheer.ConterStore", {
    extend: "app.store.BaseStore",
    alias: "widget.app_store_imms_si_kocheer_ConterStore",
    model: "app.model.imms.si.kocheer.ConterModel",
    dao: {
        selectUrl: "api/imms/si/kocheer/conter/getAll",
        deleteUrl: 'api/imms/si/kocheer/conter/deleteAll',
        insertUrl: 'api/imms/si/kocheer/conter/create',
        updateUrl: 'api/imms/si/kocheer/conter/update',
    }
});