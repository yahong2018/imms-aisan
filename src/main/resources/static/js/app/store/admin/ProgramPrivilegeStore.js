Ext.define('app.store.admin.ProgramPrivilegeStore', {
    extend: 'app.store.BaseStore',
    model: 'app.model.admin.ProgramPrivilegesModel',
    alias:'widget.app_store_admin_ProgramPrivilegeStore',
    dao:{
        deleteUrl: 'api/imms/admin/programPrivilege/delete.handler',
        insertUrl: 'api/imms/admin/programPrivilege/create.handler',
        updateUrl: 'api/imms/admin/programPrivilege/update.handler',
        selectUrl: 'api/imms/admin/programPrivilege/getProgramPrivilegeByProgramId.handler',
    }   
});

