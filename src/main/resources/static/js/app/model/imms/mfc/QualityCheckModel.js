Ext.define("app.model.imms.mfc.QualityCheckModel", {
    extend: "app.model.EntityModel",
    fields: [
        { name: "productionId", type: "int" },
        { name: "productionCode", type: "string" },
        { name: "productionName", type: "string" },

        { name: "workshopId", type: "int" },
        { name: "workshopCode", type: "string" },
        { name: "workshopName", type: "string" },
        
        { name: "timeOfOriginWork", type: "zhxhDate", dateFormat: "Y-m-d" },
        { name: "shiftId", type: "int" },
        { name: "qty", type: "int" },

        { name: "defectId", type: "int" },
        { name: "defectCode", type: "string" },
        { name: "defectName", type: "string" },

        { name: "wocgCode", type: "string" },
        { name: "locCode", type: "string" }
    ]
});