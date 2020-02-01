Ext.define("app.model.imms.mfc.ProductionMovingModel", {
    extend: "app.model.EntityModel",
    requires: ["app.ux.ZhxhDate"],
    fields: [
        { name: "productionId", type: "int" },
        { name: "productionCode", type: "string" },
        { name: "productionName", type: "string" },

        { name: "rfidCardId", type: "int" },
        { name: "rfidNo", type: "string" },

        { name: "did", type: "int" },
        { name: "gid", type: "int" },

        { name: "qty", type: "int" },

        { name: "operatorId", type: "int" },
        { name: "employeeId", type: "string" },
        { name: "employeeName", type: "string" },

        { name: "timeOfOrigin", type: "zhxhDate", dateFormat: "Y-m-d H:i:s" },

        { name: "wocgCode", type: "string" },
        { name: "workstationId", type: "int" },
        { name: "workstationCode", type: "string" },
        { name: "workstationName", type: "string" },

        { name: "workshopId", type: "int" },
        { name: "workshopCode", type: "string" },
        { name: "workshopName", type: "string" },

        { name: "workshopIdFrom", type: "int" },
        { name: "workshopCodeFrom", type: "string" },
        { name: "workshopNameFrom", type: "string" },
    ]
});