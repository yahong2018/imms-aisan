Ext.define("app.view.imms.rpt.rptProductionOrderProgress.RptProductionOrderProgressGrid", {
    extend: "app.ux.dbgrid.DbGrid",
    xtype: "app_view_imms_rpt_rptProductionOrderProgress_RptProductionOrderProgressGrid",
    requires: ["app.model.imms.mfc.ProductSummaryModel", "app.store.imms.mfc.ProductSummaryStore"],
    columns: [
        { text: "品号", dataIndex: "productionCode", width: 130, align: "center", menuDisabled: true },
        { text: "品名", dataIndex: "productionName", width: 200, align: "center", menuDisabled: true },
        { text: "车间", dataIndex: "workshopCode", width: 100, align: "center", menuDisabled: true },
        { text: "日期", dataIndex: "productDate", width: 90, align: "center", menuDisabled: true },
        {
            text: "白班", menuDisabled: true, disableSearch: true,
            columns: [
                { text: "总数", dataIndex: "qtyTotal0", align: "right",  width: 100, menuDisabled: true },
                { text: "良品数", dataIndex: "qtyGood0", align: "right",  width: 100, menuDisabled: true },
                {
                    text: "良品率", dataIndex: "rateGood0", align: "right",  width: 100, menuDisabled: true, renderer: function (v, item) {
                        if (v == 0) {
                            return "";
                        }
                        return Ext.util.Format.percent(v, "0.00");
                    }
                },
                { text: "不良数", dataIndex: "qtyDefect0", width: 100, align: "right",  menuDisabled: true },
                {
                    text: "不良率", dataIndex: "rateDefect0", width: 100, align: "right", menuDisabled: true, renderer: function (v) {
                        if (v == 0) {
                            return "";
                        }
                        return Ext.util.Format.percent(v, "0.00");
                    }
                },
            ]
        }, {
            text: "晚班", menuDisabled: true, disableSearch: true,
            columns: [
                { text: "总数", dataIndex: "qtyTotal1", width: 100, align: "right", menuDisabled: true },
                { text: "良品数", dataIndex: "qtyGood1", width: 100, align: "right",  menuDisabled: true },
                {
                    text: "良品率", dataIndex: "rateGood1", width: 100, align: "right", menuDisabled: true, renderer: function (v) {
                        if (v == 0) {
                            return "";
                        }
                        return Ext.util.Format.percent(v, "0.00");
                    }
                },
                { text: "不良数", dataIndex: "qtyDefect1", width: 100, align: "right", menuDisabled: true },
                {
                    text: "不良率", dataIndex: "rateDefect1", width: 100, align: "right", menuDisabled: true, renderer: function (v) {
                        if (v == 0) {
                            return "";
                        }
                        return Ext.util.Format.percent(v, "0.00");
                    }
                },
            ]
        },
        {
            text: "小计", menuDisabled: true, disableSearch: true,
            columns: [
                { text: "总数", dataIndex: "qtyTotal", width: 100, align: "right", menuDisabled: true },
                { text: "良品数", dataIndex: "qtyGood", width: 100, align: "right", menuDisabled: true },
                {
                    text: "良品率", dataIndex: "rateGood", width: 100, align: "right", menuDisabled: true, renderer: function (v) {
                        if (v == 0) {
                            return "";
                        }
                        return Ext.util.Format.percent(v, "0.00");
                    }
                },
                { text: "不良数", dataIndex: "qtyDefect", width: 100, align: "right",  menuDisabled: true },
                {
                    text: "不良率", dataIndex: "rateDefect", width: 100, align: "right",  menuDisabled: true, renderer: function (v) {
                        if (v == 0) {
                            return "";
                        }
                        return Ext.util.Format.percent(v, "0.00");
                    }
                },
            ]
        }
    ],
    constructor: function (config) {
        var theStore;
        if (config.filter) {
            theStore = Ext.create({ xtype: 'imms_mfc_ProductSummaryStore', autoLoad: false, pageSize: 0 });            
        } else {
            theStore = Ext.create({ xtype: 'imms_mfc_ProductSummaryStore' });
        }
        var configBase = { store: theStore };
        Ext.applyIf(config, configBase);

        this.callParent(arguments);
    },
    listeners:{
        afterrender:function(){
            if(this.filter){
                this.store.clearCustomFilter();
                this.store.addCustomFilter(this.filter());
                this.store.buildFilterUrl();
                this.store.load();
            }
        }
    }
})