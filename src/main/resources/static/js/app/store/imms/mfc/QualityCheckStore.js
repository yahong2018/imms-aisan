Ext.define("app.store.imms.mfc.QualityCheckStore", {
    extend: "app.store.BaseStore",
    alias: 'widget.imms_mfc_QualityCheckStore',
    model: "app.model.imms.mfc.QualityCheckModel",
    dao: {
        deleteUrl: 'api/imms/mes/mfc/qualityCheck/delete',
        insertUrl: 'api/imms/mes/mfc/qualityCheck/create',
        updateUrl: 'api/imms/mes/mfc/qualityCheck/update',
        selectUrl: 'api/imms/mes/mfc/qualityCheck/getAll',
    }
});