Ext.define("app.store.imms.mfc.ProductSummaryStore", {
    extend: "app.store.BaseStore",
    model: "app.model.imms.mfc.ProductSummaryModel",
    alias: "widget.imms_mfc_ProductSummaryStore",
    dao: {
        selectUrl: "api/imms/mes/mfc/productSummary/getAll"
    }
});