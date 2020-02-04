Ext.define("app.view.imms.material.material.MaterialDetailForm", {
    extend: "Ext.form.Panel",
    xtype: "app_view_imms_material_material_MaterialDetailForm",
    padding: 10,
    width: 420,
    layout: "anchor",
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
            name: "materialCode",
            fieldLabel: "产品编码",
            xtype: "textfield",
            maxLength: 20,
            allowBlank: false,
            enforceMaxLength: true,
            width: 380,
        },
        {
            name: "materialName",
            fieldLabel: "产品名称",
            xtype: "textfield",
            maxLength: 50,
            allowBlank: false,
            enforceMaxLength: true,
            width: 380,
        },
        {
            xtype: "checkbox",
            name: "autoFinishedProgress",
            fieldLabel: "自动报工"
        }
    ]
});