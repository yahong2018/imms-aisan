Ext.define("app.view.imms.mfc.qualityCheck.BomTreePanel", {
    extend: 'Ext.tree.Panel',
    xtype: 'app_view_imms_mfc_qualityCheck_BomTreePanel',
    requires: ['app.store.BaseTreeStore'],
    rootVisible: false,
    store: Ext.create('app.store.BaseTreeStore',{
        fields: ["bomId","componentId","componentCode", "componentName", "materialQty", "componentQty"],
        dao:{
            selectUrl:"api/imms/mes/material/bom/getBomTree"
        }
    }),
    columns: [
        { xtype: "treecolumn", dataIndex: "componentCode", text: "组件编码", width: 350 },
        { dataIndex: "componentName", text: "组件名称", width: 350 },
        // { dataIndex: "materialQty", text: "父件用量", width: 100 },
        // { dataIndex: "componentQty", text: "子件用量", width: 100 }
    ]
});