Ext.define("app.store.imms.mfc.ProductRecordStore",{
    extend:"app.store.BaseStore",
    model:"app.model.imms.mfc.ProductRecordModel",
    alias:"widget.imms_mfc_ProductRecordStore",

    dao: {
        deleteUrl: 'api/imms/mes/mfc/productRecord/deleteAll',
        insertUrl: 'api/imms/mes/mfc/productRecord/create',
        updateUrl: 'api/imms/mes/mfc/productRecord/update',
        selectUrl: 'api/imms/mes/mfc/productRecord/getAll',
    }
});