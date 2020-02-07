Ext.define('app.store.admin.SystemRoleStore', {
    extend: 'app.store.BaseStore',
    model: 'app.model.admin.SystemRoleModel',
    alias: 'widget.app_store_admin_SystemRoleStore',
    dao: {
        deleteUrl: 'api/imms/admin/systemRole/deleteAll',
        insertUrl: 'api/imms/admin/systemRole/create',
        updateUrl: 'api/imms/admin/systemRole/update',
        selectUrl: 'api/imms/admin/systemRole/getAll',
    },

    loadRolePrivileges: function (role, callback) {
        var me = this;
        app.ux.Utils.ajaxRequest({
            url: 'api/imms/admin/systemRole/rolePrivileges?roleId=' + role.get('recordId'),
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
            url: 'api/imms/admin/systemRole/updatePrivileges?roleId=' + role.get('recordId'),
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