Ext.define("app.store.imms.material.BomStore", {
    extend: "app.store.BaseStore",
    model: 'app.model.imms.material.BomModel',
    alias: 'widget.app_store_imms_material_BomStore',

    dao: {
        deleteUrl: 'api/imms/mes/material/bom/deleteAll',
        insertUrl: 'api/imms/mes/material/bom/create',
        updateUrl: 'api/imms/mes/material/bom/update',
        selectUrl: 'api/imms/mes/material/bom/getAll',
    },

    getBom: function (material) {
        if (material == null) {
            return;
        }

        var materialCode = material.get("materialCode");
        this.getProxy().url = "api/imms/mes/material/bom/getBomByMaterialCode?materialCode=" + materialCode;

        this.load();
    }
});