Ext.define("app.model.imms.org.WorkshopModel", {
    extend: 'app.model.EntityModel',
    fields: [
        { name: "workshopCode", type: "string" },
        { name: "workshopName", type: "string" },
        { name: "workshopType", type: "int" },
        { name: "opIndex", type: "int" },
        { name: "prevIndex", type: "int" },
    ]
});