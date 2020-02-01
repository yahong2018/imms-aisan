Ext.define("app.view.imms.mfc.productRecord.ProductRecordDetailForm", {
    extend: "Ext.form.Panel",
    xtype: "imms_mfc_productRecord_ProductRecordDetailForm",
    requires: ["app.ux.field.DateTime"],
    padding: 5,
    width: 750,
    layout: "anchor",
    defaults: {
        layout: "anchor",
        anchor: "100%",
    },
    workshopStore: Ext.create({ xtype: 'imms_org_WorkshopStore', autoLoad: true, pageSize: 0 }),
    workstationStore: Ext.create({ xtype: 'imms_org_WorkstationStore', autoLoad: true, pageSize: 0 }),
    operatorStore: Ext.create({ xtype: 'imms_org_OperatorStore', autoLoad: true, pageSize: 0 }),
    materialStore: Ext.create({ xtype: 'app_store_imms_material_MaterialStore', autoLoad: true, pageSize: 0 }),
    items: [
        { name: "recordId", xtype: "hidden" },
        { name: "workshopId", xtype: "hidden" },
        { name: "workstationId", xtype: "hidden" },
        { name: "productionId", xtype: "hidden" },
        { name: "operatorId", xtype: "hidden" },
        {
            xtype: "container",
            layout: "hbox",
            margin: '0 0 3 ',
            items: [
                {
                    name: "workshopCode", fieldLabel: "车间", allowBlank: false, xtype: "textfield", width: 200, listeners: {
                        change: function (self, newValue, oldValue, eOpts) {
                            var form = this.up("imms_mfc_productRecord_ProductRecordDetailForm");
                            var record = form.workshopStore.findRecord("workshopCode", newValue, 0, false, false, true);
                            if (record != null) {
                                form.down("[name='workshopId']").setValue(record.get("recordId"));
                                form.down("[name='workshopName']").setValue(record.get("workshopName"));
                            }
                        }
                    }
                },
                { name: "workshopName", margin: '0 0 0 20', allowBlank: false, xtype: "textfield", flex: 0.8, readOnly: true },
            ]
        },
        {
            xtype: "container",
            layout: "hbox",
            margin: '0 0 3 ',
            items: [
                {
                    xtype: "container",
                    layout: "hbox",
                    margin: '0 0 3 ',
                    items: [
                        {
                            name: "workstationCode", fieldLabel: "工位", allowBlank: false, xtype: "textfield", width: 200, listeners: {
                                change: function (self, newValue, oldValue, eOpts) {
                                    var form = this.up("imms_mfc_productRecord_ProductRecordDetailForm");
                                    var record = form.workstationStore.findRecord("workstationCode", newValue, 0, false, false, true);
                                    if (record != null) {
                                        form.down("[name='workstationId']").setValue(record.get("recordId"));
                                        form.down("[name='workstationName']").setValue(record.get("workstationName"));
                                        form.down("[name='wocgCode']").setValue(record.get("wocgCode"));
                                    }
                                }
                            }
                        },
                        { name: "workstationName", margin: '0 0 0 20', width: 280,  allowBlank: false, xtype: "textfield", readOnly: true },
                    ]
                },
                { name: "wocgCode", fieldLabel: "工作中心组", margin: '0 0 0 20',allowBlank: false, xtype: "textfield", width: 200, readOnly: true  },
            ]
        },
        {
            xtype: "container",
            layout: "hbox",
            margin: '0 0 3 ',
            items: [
                {
                    name: "productionCode", fieldLabel: "产品", xtype: "textfield", allowBlank: false, width: 200, listeners: {
                        change: function (self, newValue, oldValue, eOpts) {
                            var form = this.up("imms_mfc_productRecord_ProductRecordDetailForm");
                            var record = form.materialStore.findRecord("materialCode", newValue, 0, false, false, true);
                            if (record != null) {
                                form.down("[name='productionId']").setValue(record.get("recordId"));
                                form.down("[name='productionName']").setValue(record.get("materialName"));
                            }
                        }
                    }
                },
                { name: "productionName",  margin: '0 0 0 20', allowBlank: false, xtype: "textfield", flex: 0.8, readOnly: true },
            ]
        },
        {
            xtype: "container",
            layout: "hbox",
            margin: '0 0 3 ',
            items: [
                {
                    name: "employeeId", fieldLabel: "操作员", allowBlank: false, xtype: "textfield", width: 200, listeners: {
                        change: function (self, newValue, oldValue, eOpts) {
                            var form = this.up("imms_mfc_productRecord_ProductRecordDetailForm");
                            var record = form.operatorStore.findRecord("employeeId", newValue, 0, false, false, true);
                            if (record != null) {
                                form.down("[name='operatorId']").setValue(record.get("recordId"));
                                form.down("[name='employeeName']").setValue(record.get("employeeName"));
                            }
                        }
                    }
                },
                { name: "employeeName", allowBlank: false, margin: '0 0 0 20', xtype: "textfield", flex: 0.8, readOnly: true },
            ]
        },

        {
            xtype: "container",
            layout: "hbox",
            margin: '0 0 3 ',
            items: [
                { name: "gid", fieldLabel: "工位机", xtype: "textfield", width: 250 },
                { name: "did", fieldLabel: "控制器", margin: '0 0 0 20', xtype: "textfield", flex: 0.5 },
            ]
        },
        { name: "rfidCardNo", fieldLabel: "RFID卡号", xtype: "textfield", width: 250 },       
        {
            xtype: "container",
            layout: "hbox",
            margin: '0 0 3 ',
            items: [
                { name: "timeOfOrigin", fieldLabel: "报工时间", xtype: "datetimefield",  width: 350, allowBlank: false, format: 'Y-m-d H:i:s', },
                { name: "qty", fieldLabel: "报工数量", margin: '0 0 0 20', allowBlank: false, xtype: "textfield", width: 250 },
            ]
        },

        { name: "remark", xtype: "textarea", fieldLabel: "备注", flex: 1 },
    ],
    showHiddenItems: [
        {
            name: "recordId",
            xtype: "textfield",
            fieldLabel: "业务流水号",
            readOnly: true,
        }
    ],
    onRecordLoad: function (config) {
        if (config.seq == app.ux.data.DataOperationSeq.BEFORE && config.dataMode == app.ux.data.DataMode.INSERT) {
            config.record.data.reportTime = Ext.Date.format(new Date(), 'Y-m-d H:i:s');
        }
    }
});