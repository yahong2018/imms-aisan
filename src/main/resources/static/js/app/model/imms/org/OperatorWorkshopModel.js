Ext.define("app.model.imms.org.OperatorWorkshopModel", {
    extend: "app.model.EntityModel",
    fields: [
        { name: "operatorId", type: "int" },
        { name: "workshopId", type: "string" },
        { name: "workshopCode", type: "string" },
        { name: "workshopName", type: "string" },
        { name: "selected", type: "bool" }
    ]
})