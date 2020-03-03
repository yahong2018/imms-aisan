Ext.define("app.view.imms.org.workstation.WorkstationDetailForm", {
    extend: "Ext.form.Panel",
    xtype: "imms_org_workstation_WorkstationDetailForm",
    width: 500,
    bodyPadding: 5,
    defaults: {
        layout: "anchor",
        anchor: "100%",
    },
    items: [
        {
            xtype: "hidden",
            name: "recordId"
        },
        {
            name: "workshopId",
            xtype: "hidden"
        },
        {
            name: "workstationCode",
            xtype: "textfield",
            fieldLabel: "工位代码",
            allowBlank: false,
            maxLength: 20,
            enforceMaxLength: true,
            width: 250
        }, {
            name: "workstationName",
            xtype: "textfield",
            fieldLabel: "工位名称",
            allowBlank: false,
            maxLength: 50,
            enforceMaxLength: true,
            width: 380
        }, {
            xtype: "container",
            layout: "hbox",
            margin: "3 0 5 0",
            items: [{
                name: "canReport",
                xtype: "checkbox",
                boxLabel: "报工工位",
                width:160,
            }, {
                name: "canMoveIn",
                xtype: "checkbox",
                boxLabel: "物料投入工位",
                width:160,
            },
            {
                name: "canIssueCard",
                xtype: "checkbox",
                boxLabel: "发卡工位",
                width:160,
            }]
        },
        {
            xtype: "container",
            layout: "hbox",
            margin: "3 0 5 0",
            items: [{
                name: "canOutsourceOut",
                xtype: "checkbox",
                boxLabel: "外发出厂",
                width:160,
            }, {
                name: "canOutsourceBack",
                xtype: "checkbox",
                boxLabel: "外发回厂",
                width:160,
            }]
        },
        {
            name: "wocgCode",
            xtype: "textfield",
            fieldLabel: "工作中心组",
            allowBlank: false,
            maxLength: 50,
            enforceMaxLength: true,
            width: 380
        }, {
            name: "gid",
            xtype: "textfield",
            fieldLabel: "Rfid控制器编号",
            allowBlank: false,
            maxLength: 3,
            enforceMaxLength: true,
            width: 180
        }, {
            name: "did",
            xtype: "textfield",
            fieldLabel: "Rfid工位机编号",
            allowBlank: false,
            maxLength: 3,
            enforceMaxLength: true,
            width: 180
        }, {
            name: "didTemplate",
            xtype: "textfield",
            fieldLabel: "显示模板编号",
            allowBlank: false,
            maxLength: 3,
            enforceMaxLength: true,
            width: 180
        }, {
            name: "autoReportCount",
            xtype: "textfield",
            fieldLabel: "自动工序数",
            allowBlank: false,
            maxLength: 3,
            enforceMaxLength: true,
            width: 180
        },
        {
            name: "locCode",
            xtype: "textfield",
            fieldLabel: "存储区域代码",
            allowBlank: false,
            enforceMaxLength: true,
            width: 180
        },
        {
            name: "description",
            xtype: "textarea",
            fieldLabel: "备注",
            width: 380
        }
    ],
    onRecordLoad: function (config) {
        if (config.dataMode == app.ux.data.DataMode.INSERT && config.seq == app.ux.data.DataOperationSeq.BEFORE) {
            var record = config.record;
            var grid = config.grid;
            debugger;
            record.set("workshopId", grid.store.workshop.get("recordId"));
        }
    }
});