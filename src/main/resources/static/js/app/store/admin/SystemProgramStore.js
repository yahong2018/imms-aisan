Ext.define('app.store.admin.SystemProgramStore',{
    extend:"app.store.BaseTreeStore",
    model: 'app.model.admin.SystemProgramModel',
    alias:'widget.app_store_admin_SystemProgramStore',
    dao:{
        deleteUrl: 'api/imms/mainPage/delete.handler',
        insertUrl: 'api/imms/mainPage/create.handler',
        updateUrl: 'api/imms/mainPage/update.handler',
        selectUrl: 'api/imms/mainPage/getAllMenu.handler',
        getAllByPageUrl: 'api/imms/mainPage/getAllByPage.handler',
    }
});