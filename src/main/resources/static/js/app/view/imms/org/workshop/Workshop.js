Ext.define("app.view.imms.org.workshop.Workshop", {
    extend: "app.ux.dbgrid.DbGrid",
    xtype: "org_workshop_Workshop",
    requires: ["app.model.imms.org.WorkshopModel", "app.store.imms.org.WorkshopStore"],
    uses: ["app.view.imms.org.workshop.WorkshopDetailForm"],
    hideDefaultPagebar: true,
    hideSearchBar: true,

    columns: [
        { dataIndex: "opIndex", text: "本工序", width: 80 },
        { dataIndex: "prevIndex", text: "上工序", width: 80 },
        { dataIndex: "workshopCode", text: "车间代码", width: 120 },
        { dataIndex: "workshopName", text: "车间名称", width: 200 },
        {
            dataIndex: "workshopType", text: "车间类别", width: 200, renderer: function (value) {
                {
                    var lables = ["", "1. 内部车间", "", "3.外发前工程车间", "4.外发车间", "5.外发后工程车间", "9.工务"]
                    //  0. 内部车间   3.外发前工程车间   4.外发车间   5.外发后工程车间
                    return lables[value];
                }
            }
        }
    ],
    constructor: function (config) {
        var configBase = {
            detailFormClass: 'imms_org_workshop_WorkshopDetailForm',
            detailWindowTitle: '车间管理',
            store: Ext.create({
                xtype: 'imms_org_WorkshopStore', pageSize: 0, grid: this, listeners: {
                    load: function () {
                        if (this.getCount() > 0 && !this.grid.dataProcessed) {
                            this.grid.dataProcessed = true;
                            this.grid.getSelectionModel().select(0);
                        }
                    }
                }
            })
        }
        Ext.applyIf(config, configBase);

        this.callParent(arguments);
    },

    listeners: {
        beforeselect: 'gridSelectionChanged',
    }
});