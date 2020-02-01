Ext.define('app.model.admin.SystemUserModel', {
    extend: 'app.model.EntityModel',
    requires: ["app.ux.ZhxhDate"],
    fields: [
        { name: "userCode", type: "string", unique: true },
        { name: "displayName", type: "string" },
        { name: "pwd", type: "string" },
        { name: "accountStatus", type: "string" },
        { name: "email", type: "string" },
        { name: "online", type: "boolean" },
        { name: "recordCreationType", type: "string" },
        { name: "lastLoginTime", type: 'zhxhDate', dateFormat: 'Y-m-d H:i:s' },
    ]
});