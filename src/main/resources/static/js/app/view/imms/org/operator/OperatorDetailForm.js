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
            xtype: "hidden",
            name: "recordId"
        },
        {
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