Ext.define("app.view.admin.systemParam.SystemParamDetailForm", {
    extend: "Ext.form.Panel",
    xtype: "admin_systemParam_SystemParamDetailForm",
    bodyPadding: 5,
    width:500,
    items: [
        {
            xtype:"hidden",
            name:"recordId"
        },
        {
            xtype:"hidden",
            name:"recordCreationType"
        },
        {
            xtype: "textfield",
            name: "paramType",
            fieldLabel:"参数类别",
            readOnly:true,
            width:300
        }, {
            xtype: "textfield",
            name: "paramCode",
            fieldLabel: "参数代码",
            readOnly: true,
            width: 300,
        }, {
            xtype: "textfield",
            name: "paramDescription",
            fieldLabel: "参数含义",
            readOnly: true,
            width: "100%",
        }, {
            xtype: "textarea",
            name: "paramValue",
            fieldLabel: "参数值",
            width:"100%"
        }
    ]
});