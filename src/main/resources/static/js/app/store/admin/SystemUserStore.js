Ext.define('app.store.admin.SystemUserStore', {
    extend: 'app.store.BaseStore',
    model: 'app.model.admin.SystemUserModel',
    alias: 'widget.app_store_admin_SystemUserStore',
    uses: ['app.model.admin.UserRoleModel', 'app.ux.Utils','Ext.window.Toast'],
    dao: {
        deleteUrl: 'admin/systemUser/deleteAll',
        insertUrl: 'admin/systemUser/create',
        updateUrl: 'admin/systemUser/update',
        selectUrl: 'admin/systemUser/getAll',
    },

    restePassword: function (user) {
        var me = this;
        app.ux.Utils.ajaxRequest({
            url: 'api/admin/systemUser/resetPassword?userId=' + user.get('recordId'),
            successCallback: function (record, response, opts) {
                Ext.Msg.alert('系统提示', '密码已重设为系统缺省密码!');
            }
        });
    },
    startUser: function (user) {
        var me = this;
        app.ux.Utils.ajaxRequest({
            url: 'api/admin/systemUser/enable?userId=' + user.get('recordId'),
            successCallback: function (record, response, opts) {                           
                Ext.toast({
                    html: '用户已启用',
                    title: '系统提示',
                    width: 200,
                    align: 't'
                });               
                me.load();
            }
        });
    },
    stopUser: function (user) {
        var me = this;
        app.ux.Utils.ajaxRequest({
            url: 'api/admin/systemUser/disable?userId=' + user.get('recordId'),
            successCallback: function (record, response, opts) {                                
                Ext.toast({
                    html: '用户已停用',
                    title: '系统提示',
                    width: 200,
                    align: 't'
                });    
                me.load();
            }
        });
    },
    beforeUpdate: function (current, old) {
        current.password = old.get('password');
    },    
    updateUserRoles: function (win, user, roles) {
        debugger;
        var me = this;        
        var userId = user.get('recordId');
        app.ux.Utils.ajaxRequest({
            url: 'api/admin/systemUser/updateUserRoles?userId=' + userId,
            method: 'POST',
            jsonData: roles,
            successCallback: function (result, response, opts) {
                me.load();
                
                win.close();
                win.destroy();

                Ext.toast({
                    html: '数据已保存',
                    title: '系统提示',
                    width: 200,
                    align: 't'
                });
            }
        });
    }
});