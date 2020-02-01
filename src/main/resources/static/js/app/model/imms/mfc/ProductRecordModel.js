Ext.define("app.model.imms.mfc.ProductRecordModel", {
    extend: "app.model.EntityModel",
    requires: ["app.ux.ZhxhDate"],
    fields: [
        { name: "workshopId", type: "int" },
        { name: "workshopCode", type: "string" },
        { name: "workshopName", type: "string" },

        { name: "workstationId", type: "int" },
        { name: "workstationCode", type: "string" },
        { name: "workstationName", type: "string" },
        { name: "wocgCode", type: "string" },

        { name: "productionId", type: "int" },
        { name: "productionCode", type: "string" },
        { name: "productionName", type: "string" },

        { name: "operatorId", type: "int" },
        { name: "employeeId", type: "string" },
        { name: "employeeName", type: "string" },

        { name: "gid", type: "int" },
        { name: "did", type: "int" },

        { name: "shiftId", type: "int" },
        { name: "timeOfOrigin", type: 'zhxhDate', dateFormat: 'Y-m-d H:i:s' },
        { name: "timeOfOriginWork", type: 'zhxhDate', dateFormat: 'Y-m-d' },

        { name: "qty", type: "int" },
        { name: "cardQty", type: "int" },

        { name: "rfidCardId", type: "int" },
        { name: "rfidCardNo", type: "string" },
        { name: "reportType", type: "int" },

        { name: "remark", type: "string" },
    ]
});