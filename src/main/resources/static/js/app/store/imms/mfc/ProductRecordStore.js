Ext.define("app.store.imms.mfc.ProductRecordStore",{
    extend:"app.store.BaseStore",
    model:"app.model.imms.mfc.ProductRecordModel",
    alias:"widget.imms_mfc_ProductRecordStore",

    dao: {
        deleteUrl: 'imms/mfc/productRecord/deleteAll',
        insertUrl: 'imms/mfc/productRecord/create',
        updateUrl: 'imms/mfc/productRecord/update',
        selectUrl: 'imms/mfc/productRecord/getAll',
    }
});