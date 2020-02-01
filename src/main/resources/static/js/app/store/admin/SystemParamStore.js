Ext.define("app.store.admin.SystemParamStore",{
    extend:"app.store.BaseStore",
    model:"app.model.admin.SystemParamModel",
    alias:"widget.app_store_admin_SystemParamStore",
    dao:{
        deleteUrl: 'admin/systemParam/deleteAll',
        insertUrl: 'admin/systemParam/create',
        updateUrl: 'admin/systemParam/update',
        selectUrl: 'admin/systemParam/getAll',
    }
});