Ext.define("app.store.imms.mfc.ProductionMovingStore",{
    extend:"app.store.BaseStore",
    model:"app.model.imms.mfc.ProductionMovingModel",
    alias:"widget.imms_mfc_ProductionMovingStore",

    dao:{
        deleteUrl: 'api/imms/mes/mfc/productionOrderMoving/delete',
        insertUrl: 'api/imms/mes/mfc/productionOrderMoving/create',
        updateUrl: 'api/imms/mes/mfc/productionOrderMoving/update',
        selectUrl: 'api/imms/mes/mfc/productionOrderMoving/getAll',
    }

});