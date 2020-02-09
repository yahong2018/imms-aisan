Ext.define("app.view.imms.si.kocheer.conter.ConterDetailForm", {
    extend: "Ext.form.Panel",
    xtype: "app_view_imms_si_kocheer_conter_ConterDetailForm",
    uses: ["app.store.imms.si.kocheer.StationStore"],
    padding: 5,
    width: 600,
    layout: "anchor",
    defaults: {
        layout: "anchor",
        anchor: "100%",
    },
    items: [
        { name: "recordId", xtype: "hidden" },
        {

            xtype: "combobox", name: "stationID", fieldLabel: "站点", allowBlank: false,
            valueField: "recordId", displayField: "stationName",
            store: Ext.create("app.store.imms.si.kocheer.StationStore", { autoLoad: true, pageSize: 0 }),
            listeners: {
                change: function (self, newValue, oldValue, eOpts) {
                    var form = this.up("app_view_imms_si_kocheer_conter_ConterDetailForm");
                    var record = self.getSelectedRecord();
                    if (record != null) {
                        form.down("[name='stationID']").setValue(record.get("recordId"));
                    }
                }
            }
        },
        {
            xtype: "container",
            layout: "hbox",
            margin: '0 0 3 ',
            items: [
                { name: "conterName", xtype: "textfield", fieldLabel: "名称", allowBlank: false, },
                { name: "GID", xtype: "textfield", margin: '0 20 0 20', fieldLabel: "组号", allowBlank: false }]
        },
        {
            xtype: "container",
            layout: "hbox",
            margin: '0 0 3 ',
            items: [

                { name: "startDID", xtype: "textfield", fieldLabel: "开始机号", allowBlank: false },
                { name: "endDID", xtype: "textfield", margin: '0 20 0 20', fieldLabel: "结束机号", allowBlank: false }
            ]
        },
        {
            xtype: "container",
            layout: "hbox",
            margin: '0 0 3 ',
            items: [
                { name: "IP", xtype: "textfield", fieldLabel: "IP", allowBlank: false },
                { name: "port", xtype: "textfield", margin: '0 20 0 20', fieldLabel: "端口", allowBlank: false },
            ]
        }, {
            xtype: "container",
            layout: "hbox",
            margin: '0 0 3 ',
            items: [
                { name: "isUse", xtype: "checkbox", fieldLabel: "是否启用", inputValue: 1,width:275 },
                { name: "wiressPower", xtype: "textfield", margin: '0 20 0 20', fieldLabel: "无线功率", allowBlank: false },
            ]
        }
    ]
})