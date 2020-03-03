Ext.define("app.model.imms.org.WorkstationModel", {
    extend: "app.model.EntityModel",
    fields: [
        { name: "workstationCode", type: "string" },
        { name: "workstationName", type: "string" },
        { name: "workstationType", type: "int" },
        { name: "description", type: "string" },
        { name: "workshopId", type: "int" },
        { name: "gid", type: "int" },
        { name: "did", type: "int" },
        { name: "didTemplate", type: "int" },
        { name: "wocgCode", type: "string" },
        { name: "autoReportCount", type: "int" },
        { name: "locCode", type: "string" },
        
        { name: "canReport", type: "boolean" },
        { name: "canMoveIn", type: "boolean" },
        { name: "canIssueCard", type: "boolean" },
        { name: "canOutsourceOut", type: "boolean" },
        { name: "canOutsourceBack", type: "boolean" }
    ]
});