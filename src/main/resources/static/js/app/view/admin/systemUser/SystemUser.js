Ext.define('app.view.admin.systemUser.SystemUser', {
    extend: 'app.ux.dbgrid.DbGrid',
    xtype: 'app_view_admin_systemUser_SystemUser',
    requires: ['app.view.admin.systemUser.SystemUserController', 'app.view.admin.systemUser.SystemUserModel'],
    uses: ['app.view.admin.systemUser.SystemUserDetailForm', 'app.model.admin.SystemUserModel', 'app.store.admin.SystemUserStore', "app.view.admin.systemUser.SystemUserSearchForm"],
    controller: {
        type: 'SystemUserController'
    },
    viewModel: {
        type: 'SystemUserModel'
    },
    columns: [
        { dataIndex: 'userCode', text: '用户账号', width: 100 },
        { dataIndex: 'displayName', text: '账号名称', width: 100 },
        {
            dataIndex: 'accountStatus', text: '启用状态', width: 150, renderer: function (value) {                
                {
                    if (value == 'EXPIRED') {
                        return "停用(EXPIRED)";
                    }
                    return "启用(NORMAL)";
                }
            }
        },        
        { dataIndex: 'lastLoginTime', text: '最后登录', width: 200 },
        { dataIndex: 'email', text: 'email', flex: 1 }
    ],
    additionToolbarItems: [
        '-',
        { text: '启用账户', handler: 'startUser', privilege: "START_USER" },
        { text: '停用账户', handler: 'stopUser', privilege: "STOP_USER" },
        { xtype: 'tbspacer' },
        { text: '密码重设', handler: 'resetPassword', privilege: "RESET_PASSWORD" },
        '-',
        { text: '授权', handler: 'updateRoles', privilege: "ASSIGN_ROLE" }
    ],

    constructor: function (config) {
        var configBase = {
            store: Ext.create({ xtype: 'app_store_admin_SystemUserStore' }),
            detailFormClass: 'admin_systemUser_SystemUserDetailForm',
            detailWindowTitle: '登录账户管理'
        }
        Ext.applyIf(config, configBase);

        this.callParent(arguments);
    },
    listeners: {
        afterrender: function () {
            this.getController().loadAllRoles();
        },
        // beforeselect: 'gridSelectionChanged',
    }
});