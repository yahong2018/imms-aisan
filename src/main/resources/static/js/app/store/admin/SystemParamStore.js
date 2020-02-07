Ext.define("app.store.admin.SystemParamStore",{
    extend:"app.store.BaseStore",
    model:"app.model.admin.SystemParamModel",
    alias:"widget.app_store_admin_SystemParamStore",
    dao:{
        deleteUrl: 'api/imms/admin/systemParam/deleteAll',
        insertUrl: 'api/imms/admin/systemParam/create',
        updateUrl: 'api/imms/admin/systemParam/update',
        selectUrl: 'api/imms/admin/systemParam/getAll',
    }
});