Ext.define("app.view.imms.org.operator.WorkshopConfigForm", {
    extend: "Ext.form.Panel",
    xtype: "app_view_imms_org_operator_WorkshopConfigForm",
    requires: ["app.model.imms.org.WorkshopModel", "app.store.imms.org.WorkshopStore"],
    workshopStore: Ext.create({ xtype: "imms_org_WorkshopStore", pageSize: 0, autoLoad: true }),    
    items: [{
        xtype: 'checkboxgroup',
        columns: 5,
        items: []
    }],

    loadData: function (operator) {
        // debugger;
        // var me = this;
        // var workshopGroup = me.down('checkboxgroup');
        // workshopGroup.items = [];

        // var belongWorkshops = operator.get("belongWorkshops") || "";
        // belongWorkshops = belongWorkshops.split(";");

    },

    initComponent: function () {        
        var me = this;
        var workshopGroup = me.items[0];
        workshopGroup.items=[];        
        var records = me.workshopStore.getData();
        for (var i = 0; i < records.length; i++) {
            var workshop = records.getAt(i);
            workshopGroup.items.push(
                {
                    boxLabel: workshop.get('workshopName'),
                    inputValue: workshop.get('recordId'),
                    name: 'operatorWorkshops',
                });
        }

        this.callParent(arguments);    
    }
});