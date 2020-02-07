Ext.define("app.view.imms.org.operator.Operator", {
    extend: "app.ux.dbgrid.DbGrid",
    xtype: "app_view_imms_org_operator_Operator",
    requires: ["app.model.imms.org.OperatorModel", "app.store.imms.org.OperatorStore",
        "app.model.imms.org.WorkshopModel", "app.store.imms.org.WorkshopStore"],
    uses: ["app.view.imms.org.operator.OperatorDetailForm", "app.view.imms.org.operator.WorkshopConfigForm"],
    columns: [
        { dataIndex: "employeeId", text: "工号", width: 100 },
        { dataIndex: "employeeName", text: "姓名", width: 100 },
        { dataIndex: "employeeCardNo", text: "工卡号", width: 100 },
        {
            dataIndex: "belongedWorkshops", text: "所属车间", flex: 1, renderer: function (v) {
                if (v == null) {
                    return;
                }

                var result = "";
                for (var i = 0; i < v.length; i++) {
                    result = result + v[i].workshopName + ";";
                }
                return result;
            }
        },
    ],

    additionToolbarItems: [
        '-',
        {
            text: '配置部门', privilege: "CONFIG_WORKSHOP",
            handler: function () {
                var grid = this.up("app_view_imms_org_operator_Operator");
                var operator = grid.getSelectedRecord();
                if (operator == null) {
                    Ext.Msg.alert("系统提示", "请先选择一条记录！");
                    return;
                }
                var deailWindow = Ext.create('app.ux.ModalWindow', {
                    modal: true,
                    width: 680,
                    title: '部门配置',
                    parentView: grid,
                    doSave: grid.updateWorkshop,
                    items: [{
                        xtype: 'app_view_imms_org_operator_WorkshopConfigForm',
                    }]
                });

                var form = deailWindow.down('app_view_imms_org_operator_WorkshopConfigForm');
                var checkboxGroup = form.down('checkboxgroup');
                var workshopIdList = [];
                var workshopList = operator.get("belongedWorkshops");
                for (var i = 0; i < workshopList.length; i++) {
                    workshopIdList.push(workshopList[i].recordId + "");
                }
                checkboxGroup.setValue({ operatorWorkshops: workshopIdList });

                deailWindow.show();
            }
        },
    ],
    updateWorkshop: function (grid, win) {
        var checkboxGroup = win.down('checkboxgroup');
        var operator = grid.getSelectedRecord();
        var workshopIdList = checkboxGroup.getValue().operatorWorkshops;
        if ((typeof workshopIdList) === "string") {
            var tmp = [];
            tmp.push(workshopIdList);
            workshopIdList = tmp;
        } else if ((typeof workshopIdList) === "undefined") {
            workshopIdList = [];
        }

        app.ux.Utils.ajaxRequest({
            url: 'api/imms/mes/org/operator/updateOperatorWorkshop?operatorId=' + operator.get("recordId"),
            method: "POST",
            jsonData: workshopIdList,
            successCallback(result, response, opts) {
                Ext.toast({
                    html: '数据已保存',
                    title: '系统提示',
                    width: 200,
                    align: 't'
                });
                grid.store.load();
                win.close();
            }
        });
    },
    constructor: function (config) {
        var configBase = {
            detailFormClass: 'imms_org_operator_OperatorDetailForm',
            detailWindowTitle: '员工管理',
            store: Ext.create({ xtype: 'imms_org_OperatorStore' })
        }
        Ext.applyIf(config, configBase);

        this.callParent(arguments);
    }
});