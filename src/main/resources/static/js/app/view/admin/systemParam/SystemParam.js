Ext.define('app.view.admin.systemParam.SystemParam', {
    extend: 'app.ux.dbgrid.DbGrid',
    xtype: 'app_view_admin_systemParam_SystemParam',
    requires:["app.model.admin.SystemParamModel","app.store.admin.SystemParamStore"],
    uses:["app.view.admin.systemParam.SystemParamDetailForm"],
    hideInsert:true,
    hideDelete:true,
    columns:[
        { dataIndex:"paramType",text:"参数类别",width:100},        
        { dataIndex: "paramCode", text: "参数代码", width: 200 },
        { dataIndex: "paramDescription", text: "参数含义", width: 200,flex:1 },
        { dataIndex: "paramValue", text: "参数值",flex:1,minWidth:300},
    ],
    additionToolbarItems: [
        '-',
        {
            text: '立即同步', privilege: "SYNC_WITH_ERP_WDB", handler: function () {
                app.ux.Utils.ajaxRequest({
                    url: "api/imms/admin/systemParam/sync_wdb",
                    method: "GET",
                    successCallback: function (result, response, opts) {
                        debugger;
                        alert(result);
                    }
                })
            }
        },
    ],

    constructor:function(config){
        var configBase = {
            store: Ext.create({ xtype: 'app_store_admin_SystemParamStore' }),
            detailFormClass: 'admin_systemParam_SystemParamDetailForm',
            detailWindowTitle: '系统参数'
        }
        Ext.applyIf(config, configBase);

        this.callParent(arguments);
    }
        
});