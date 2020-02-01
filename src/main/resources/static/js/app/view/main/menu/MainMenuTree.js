Ext.define('app.view.main.menu.MainMenuTree', {
	extend: 'Ext.tree.Panel',
	alias: 'widget.mainmenutree',
	requires: ['Ext.data.TreeStore','app.model.admin.SystemMenuTreeModel'],
	frame: true,
	rootVisible: false,
	store: {
		type:'tree',
		model: 'app.model.admin.SystemMenuTreeModel',
		proxy: {
			type: 'ajax',
			url: 'api/admin/mainPage/currentUserMenu',
			headers: app.ux.Utils.getAuthorizeHeader(),
			// reader: {
            //     type: 'json',
            //     rootProperty:"children"
			// }
		}
	},
	listeners: {
		itemclick: 'onMainMenuClick'
	}
});