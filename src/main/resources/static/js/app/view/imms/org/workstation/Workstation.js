Ext.define("app.view.imms.org.workstation.Workstation", {
    extend: "app.ux.dbgrid.DbGrid",
    xtype: "org_workstation_Workstation",
    requires: ["app.model.imms.org.WorkstationModel", "app.store.imms.org.WorkstationStore", "app.model.imms.org.WorkshopModel"],
    uses: ["app.view.imms.org.workstation.WorkstationDetailForm"],

    hideDefaultPagebar: true,
    hideSearchBar: true,

    columns: [
        { dataIndex: "workstationCode", text: "工位代码", width: 100 },
        { dataIndex: "workstationName", text: "工位名称", width: 150 },
        { dataIndex: "wocgCode", text: "工作中心组", width: 100 },
        { dataIndex: "didTemplate", text: "显示模板", width: 100 },
        { dataIndex: "gid", text: "控制器", width: 100 },
        { dataIndex: "did", text: "工位机", width: 100 },
        { dataIndex: "autoReportCount", text: "自动工序数", width: 100 },
        { dataIndex: "locCode", text: "存储区域", width: 100 },
        { dataIndex: "description", text: "备注", flex: 1 }
    ],
    constructor: function (config) {
        var configBase = {
            store: Ext.create({ xtype: 'imms_org_WorkstationStore', autoLoad: false }),
            detailFormClass: 'imms_org_workstation_WorkstationDetailForm',
            detailWindowTitle: '工位管理'
        }
        Ext.applyIf(config, configBase);

        this.callParent(arguments);
    },
});