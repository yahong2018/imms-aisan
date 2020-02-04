Ext.define('app.model.imms.material.MaterialModel', {
    extend: 'app.model.EntityModel',
    fields: [
        { name: "materialCode", type: "string" },
        { name: "materialName", type: "string" },
        { name: "autoFinishedProgress", type: "bool" },
        { name: "description", type: "string" },
    ]
});