Ext.define("app.view.imms.org.operator.OperatorDetailForm", {
    extend: "Ext.form.Panel",
    xtype: "imms_org_operator_OperatorDetailForm",
    width: 400,
    bodyPadding: 5,
    defaults: {
        labelWidth: 70
    },
    items: [
        {
            xtype:"hidden",
            name:"recordId"
        },
        {
            name: "orgCode",
            xtype: "hidden"
        }, {
            name: "orgName",
            xtype: "hidden"
        },
        {
            name: "orgId",
            xtype: "combobox",
            fieldLabel: "所属车间",
            width: 380,
            valueField: "recordId",
            displayField: "workshopName",
            store: Ext.create({ xtype: "imms_org_WorkshopStore", autoLoad: true, pageSize: 0 }),
            listeners: {
                change: function (self, newValue, oldValue, eOpts) {
                    var form = self.up("imms_org_operator_OperatorDetailForm");
                    var orgCode = form.down("[name='orgCode']");
                    var orgName = form.down("[name='orgName']");

                    var record = self.getSelectedRecord();
                    if (record != null) {
                        orgCode.setValue(record.get("workshopCode"));
                        orgName.setValue(record.get("workshopName"));
                    }
                }
            }
        }, {
            name: "employeeId",
            xtype: "textfield",
            fieldLabel: "工号",
            allowBlank: false,
            maxLength: 10,
            enforceMaxLength: true,
            width: 380
        }, {
            name: "employeeName",
            xtype: "textfield",
            fieldLabel: "姓名",
            allowBlank: false,
            maxLength: 20,
            enforceMaxLength: true,
            width: 380,
        }, {
            name: "employeeCardNo",
            xtype: "textfield",
            fieldLabel: "工卡号",
            allowBlank: false,
            maxLength: 10,
            enforceMaxLength: true,
            width: 380
        }
    ]
});