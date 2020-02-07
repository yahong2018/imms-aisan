Ext.define("app.store.imms.material.MaterialStore",{
    extend:"app.store.BaseStore",
    model: 'app.model.imms.material.MaterialModel',
    alias:'widget.app_store_imms_material_MaterialStore',
     
    dao:{
        deleteUrl: 'api/imms/mes/material/material/deleteAll',
        insertUrl: 'api/imms/mes/material/material/create',
        updateUrl: 'api/imms/mes/material/material/update',
        selectUrl: 'api/imms/mes/material/material/getAll',
    }
});