Ext.define('app.view.admin.systemUser.SystemUserController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.SystemUserController',
    uses: ['app.view.admin.systemUser.UserRolesForm', 'app.ux.ModalWindow', 'app.store.admin.SystemRoleStore'],

    resetPassword: function () {
        var grid = this.getView();
        app.ux.Utils.verifySelection(grid, function (record) {
            Ext.Msg.confirm('系统提示', '是否要重设该账号的密码？', function (buttonId) {
                if (buttonId == 'yes') {
                    grid.getStore().resetPassword(record);
                }
            });
        });
    },
    loadAllRoles: function () {
        var me = this;
        var roleStore = Ext.create({ xtype: 'app_store_admin_SystemRoleStore', autoLoad: false });
        roleStore.load(function (records, operation, success) {
            me.getView().getViewModel().set('allRoles', records);
        });
    },
    startUser: function () {
        var grid = this.getView();
        app.ux.Utils.verifySelection(grid, function (record) {
            grid.getStore().startUser(record);
        });
    },
    stopUser: function () {
        var grid = this.getView();
        app.ux.Utils.verifySelection(grid, function (record) {
            grid.getStore().stopUser(record);
        });
    },
    updateRoles: function () {
        var me = this;
        var grid = me.getView();
        var doSaveMethod = me.doUpdateRoles;
        var users = grid.getSelectionModel().getSelection();
        if (users == null || users.length == 0) {
            return;
        }

        var deailWindow = Ext.create('app.ux.ModalWindow', {
            modal: true,
            width: 400,
            title: '系统授权',
            parentView: grid,
            doSave: doSaveMethod,
            items: [{
                xtype: 'systemUser_UserRolesForm',
                allRoles: grid.getViewModel().get('allRoles')
            }]
        });
        var form = deailWindow.down('systemUser_UserRolesForm');
        var checkboxGroup = form.down('checkboxgroup');
        var userRoleIdList = [];
        var roles = users[0].get("roles");
        for (var i = 0; i < roles.length; i++) {
            userRoleIdList.push(roles[i].recordId + "");
        }
        checkboxGroup.setValue({ userRoles: userRoleIdList });

        deailWindow.show();
    },

    doUpdateRoles: function (grid, win) {
        var checkboxGroup = win.down('checkboxgroup');
        var roleIdList = checkboxGroup.getValue().userRoles;
        var roles = [];
        var allRoles = grid.getViewModel().get('allRoles');
        if ((typeof roleIdList) === "string") {
            var tmp = [];
            tmp.push(roleIdList);
            roleIdList = tmp;
        } else if ((typeof roleIdList) === "undefined") {
            roleIdList = [];
        }

        for (var i = 0; i < roleIdList.length; i++) {
            for (var j = 0; j < allRoles.length; j++) {
                if (allRoles[j].get('recordId') == roleIdList[i]) {
                    roles.push(allRoles[j].data);
                    break;
                }
            }
        }
        var user = grid.getSelectionModel().getSelection()[0];
        var userId = user.get('recordId');
        grid.getStore().updateUserRoles(win, user, roles);
    },

    gridSelectionChanged: function (model, selected, index) {
        var grid = this.getView();

        var disabled = selected.get("recordCreationType") == "BUILD_IN";
        var btnEdit = grid.down('dbgrideditbutton');
        var btnDelete = grid.down('dbgriddeletebutton');
        var btnStart = grid.down('[privilege="START_USER"]');
        var btnStop = grid.down('[privilege="STOP_USER"]');
        var btnResetPassword = grid.down('[privilege="RESET_PASSWORD"]');
        var btnAssign = grid.down('[privilege="ASSIGN_ROLE"]');

        btnEdit.setDisabled(disabled);
        btnDelete.setDisabled(disabled);
        btnStart.setDisabled(disabled);
        btnStop.setDisabled(disabled);
        btnResetPassword.setDisabled(disabled);
        btnAssign.setDisabled(disabled);
    }
});