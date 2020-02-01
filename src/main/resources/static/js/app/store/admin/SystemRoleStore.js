Ext.define('app.store.admin.SystemRoleStore', {
    extend: 'app.store.BaseStore',
    model: 'app.model.admin.SystemRoleModel',
    alias: 'widget.app_store_admin_SystemRoleStore',
    dao: {
        deleteUrl: 'admin/systemRole/deleteAll',
        insertUrl: 'admin/systemRole/create',
        updateUrl: 'admin/systemRole/update',
        selectUrl: 'admin/systemRole/getAll',
    },

    loadRolePrivileges: function (role, callback) {
        var me = this;
        app.ux.Utils.ajaxRequest({
            url: 'api/admin/systemRole/rolePrivileges?roleId=' + role.get('recordId'),
            successCallback: function (result, response, opts) {
                if (callback) {
                    callback.apply(me, [role, result]);
                }
            }
        })
    },
    updateRolePrivilege: function (role, privilegeList, callback) {
        var me = this;
       
        app.ux.Utils.ajaxRequest({
            url: 'api/admin/systemRole/updatePrivileges?roleId=' + role.get('recordId'),
            method: 'POST',
            jsonData: privilegeList,
            successCallback: function (result, response, opts) {
                if (callback) {
                    callback.apply(me, [role, result]);
                }
            }
        })
    }
});