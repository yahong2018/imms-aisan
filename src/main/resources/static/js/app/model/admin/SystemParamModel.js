Ext.define("app.model.admin.SystemParamModel", {
    extend: "app.model.EntityModel",
    fields: [
        { name: "paramType", type: "string" },        
        { name: "paramCode", type: "string" },
        { name: "paramDescription", type: "string" },
        { name: "paramValue", type: "string" },
        { name: "recordCreationType", type: "string" },
    ]
});
