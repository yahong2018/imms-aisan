Ext.define("app.model.imms.si.kocheer.StationModel", {
    extend: "app.model.EntityModel",
    requires: ["app.ux.ZhxhDate"],
    fields: [
        { name: "stationCode", type: "string" },
        { name: "stationName", type: "string" },
        { name: "stationPosition", type: "string" },
        { name: "softWareName", type: "string" },
        { name: "softWareVersion", type: "string" },
        { name: "stationIP", type: "string" },
        { name: "stationLoginState", type: "int" },
        { name: "loginUserID", type: "int" },
        { name: "loginName", type: "string" },
        { name: "userName", type: "string" },
        { name: "userType", type: "int" },
        { name: "userID", type: "int" },
        { name: "firstLoginTime", type: "zhxhDate", dateFormat: "Y-m-d H:i:s" },
        { name: "lastLoginTime", type: "zhxhDate", dateFormat: "Y-m-d H:i:s" },
        { name: "lastLogOutTime", type: "zhxhDate", dateFormat: "Y-m-d H:i:s" },
        { name: "lastStateUpdateTime", type: "zhxhDate", dateFormat: "Y-m-d H:i:s" },
        { name: "isUse", type: "int" }
    ]
});