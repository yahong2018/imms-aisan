Ext.define("app.store.imms.org.OperatorStore", {
    extend: "app.store.BaseStore",
    model: 'app.model.imms.org.OperatorModel',
    alias: 'widget.imms_org_OperatorStore',

    dao: {
        deleteUrl: 'api/imms/mes/org/operator/deleteAll',
        insertUrl: 'api/imms/mes/org/operator/create',
        updateUrl: 'api/imms/mes/org/operator/update',
        selectUrl: 'api/imms/mes/org/operator/getAll',
    }
});