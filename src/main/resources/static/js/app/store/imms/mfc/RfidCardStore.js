Ext.define("app.store.imms.mfc.RfidCardStore", {
    extend: "app.store.BaseStore",
    alias: 'widget.imms_mfc_RfidCardStore',
    model: "app.model.imms.mfc.RfidCardModel",
    dao: {
        deleteUrl: 'api/imms/mes/mfc/rfidCard/deleteAll',
        insertUrl: 'api/imms/mes/mfc/rfidCard/create',
        updateUrl: 'api/imms/mes/mfc/rfidCard/update',
        selectUrl: 'api/imms/mes/mfc/rfidCard/getAll',
    }
});