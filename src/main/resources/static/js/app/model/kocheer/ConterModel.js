Ext.define("app.model.kocheer.ConterModel", {
    extend: "app.model.EntityModel",
    fields: [
        { name: "stationID", type: "int" },
        { name: "GID", type: "int" },
        { name: "conterName", type: "string" },
        { name: "startDID", type: "int" },
        { name: "endDID", type: "int" },
        { name: "IP", type: "string" },
        { name: "port", type: "int" },
        { name: "position", type: "string" },
        { name: "isUse", type: "int" },
        { name: "wiressPower", type: "int" },
        { name: "remark", type: "string" }
    ]
});