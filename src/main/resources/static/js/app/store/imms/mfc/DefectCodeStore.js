Ext.define("app.store.imms.mfc.DefectCodeStore",{
    extend:"app.store.BaseStore",
    model:"app.model.imms.mfc.DefectCodeModel",
    alias: "widget.imms_mfc_DefectCodeStore",

    dao: {
        deleteUrl: 'imms/mfc/defectCode/deleteAll',
        insertUrl: 'imms/mfc/defectCode/create',
        updateUrl: 'imms/mfc/defectCode/update',
        selectUrl: 'imms/mfc/defectCode/getAll',
    }
});