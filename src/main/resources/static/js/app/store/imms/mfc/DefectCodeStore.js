Ext.define("app.store.imms.mfc.DefectCodeStore",{
    extend:"app.store.BaseStore",
    model:"app.model.imms.mfc.DefectCodeModel",
    alias: "widget.imms_mfc_DefectCodeStore",

    dao: {
        deleteUrl: 'api/imms/mes/mfc/defectCode/deleteAll',
        insertUrl: 'api/imms/mes/mfc/defectCode/create',
        updateUrl: 'api/imms/mes/mfc/defectCode/update',
        selectUrl: 'api/imms/mes/mfc/defectCode/getAll',
    }
});