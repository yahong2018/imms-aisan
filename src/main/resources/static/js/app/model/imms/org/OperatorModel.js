Ext.define("app.model.imms.org.OperatorModel", {
    extend: "app.model.EntityModel",
    fields: [
        { name: "employeeId", type: "string" },
        { name: "employeeName", type: "string" },
        { name: "employeeCardNo", type: "string" },
        //{ name: "belongWorkshops", type: "string" },        
    ]
});