Ext.define("app.view.imms.mfc.productRecord.ProductRecord", {
    extend: "app.ux.dbgrid.DbGrid",
    xtype: "app_view_imms_mfc_productRecord_ProductRecord",
    requires: ["app.model.imms.mfc.ProductRecordModel", "app.store.imms.mfc.ProductRecordStore",
        "app.model.imms.mfc.RfidCardModel", "app.store.imms.mfc.RfidCardStore",
        "app.model.imms.org.WorkstationModel", "app.store.imms.org.WorkstationStore",
        "app.model.imms.org.WorkshopModel", "app.store.imms.org.WorkshopStore",
        "app.model.imms.material.MaterialModel", "app.store.imms.material.MaterialStore",
        "app.model.imms.org.OperatorModel", "app.store.imms.org.OperatorStore"
    ],
    uses: ["app.view.imms.mfc.productRecord.ProductRecordDetailForm"],
    columns: [
        { dataIndex: "recordId", text: "业务流水" },
        { dataIndex: "workshopCode", text: "车间编码" },
        { dataIndex: "workshopName", text: "车间名称" },

        { dataIndex: "workstationCode", text: "工位编码" },
        { dataIndex: "workstationName", text: "工位名称" },
        { dataIndex: "wocgCode", text: "工作中心组" },

        { dataIndex: "productionCode", text: "产品编码", width: 150 },
        { dataIndex: "productionName", text: "产品名称", width: 200 },
        { dataIndex: "timeOfOrigin", text: '时间', width: 150 },
        { dataIndex: "qty", text: "数量" },

        { dataIndex: "employeeId", text: "工号" },
        { dataIndex: "employeeName", text: "姓名" },

        { dataIndex: "rfidTerminatorId", text: "工位机" },
        { dataIndex: "rfidControllerId", text: "控制器" },
        { dataIndex: "rfidCardNo", text: "卡号" },
        {
            dataIndex: "reportType", text: "汇报类型", renderer: function (v) {
                if (v == 0) {
                    return "0.整数报工";
                }
                return "1.尾数报工";
            }
        },
    ],

    constructor: function (config) {
        var configBase = {
            store: Ext.create({ xtype: 'imms_mfc_ProductRecordStore' }),
            detailFormClass: 'imms_mfc_productRecord_ProductRecordDetailForm',
            detailWindowTitle: '生产报工',
        };
        Ext.applyIf(config, configBase);

        this.callParent(arguments);
    }
});