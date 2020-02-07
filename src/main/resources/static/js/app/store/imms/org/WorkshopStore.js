Ext.define("app.store.imms.org.WorkshopStore",{
    extend:"app.store.BaseStore",
    model: 'app.model.imms.org.WorkshopModel',
    alias:'widget.imms_org_WorkshopStore',
     
    dao:{
        deleteUrl: 'api/imms/mes/org/workshop/deleteAll',
        insertUrl: 'api/imms/mes/org/workshop/create',
        updateUrl: 'api/imms/mes/org/workshop/update',
        selectUrl: 'api/imms/mes/org/workshop/getAll',
    }
});