Ext.define("app.view.imms.si.kocheer.conter.Conter", {
    extend: "app.ux.dbgrid.DbGrid",
    xtype: "app_view_imms_si_kocheer_conter_Conter",
    requires: ["app.model.imms.si.kocheer.ConterModel", "app.store.imms.si.kocheer.ConterStore"],
    requires: ["app.view.imms.si.kocheer.conter.ConterDefailForm"],
    columns: [
        { dataIndex: "stationName", text: "站点" },
        { dataIndex: "conterName", text: "名称" },
        { dataIndex: "GID", text: "组号" },
        { dataIndex: "startDID", text: "开始机号" },
        { dataIndex: "endDID", text: "结束机号" },
        { dataIndex: "IP", text: "IP地址" },
        { dataIndex: "port", text: "端口" },        
        {
            dataIndex: "isUse", text: "是否启用", renderer: function (v) {
                if (v == 0) {
                    return "0.不启用";
                }
                return "1.启用";
            }
        },
        { dataIndex: "wiressPower", text: "无线功率" },
    ],
    constructor: function (config) {
        var configBase = {
            detailFormClass: 'app_view_imms_si_kocheer_conter_ConterDefailForm',
            detailWindowTitle: '控制器',
            store: Ext.create({ xtype: 'app_store_imms_si_kocheer_ConterStore' })
        }
        Ext.applyIf(config, configBase);

        this.callParent(arguments);
    }
});