Ext.Loader.setConfig({
    enabled : true
});

Ext.Loader.setPath({
    // 'Ext.ux' : jsRoot + '/ext/ux',
    // 'Ext.app' : jsRoot + '/ext/app',
    'app' : 'js/app',
  //  'tmpl' : jsRoot + "/template/manage/create",
  //  'common' : jsRoot + '/zhxh'
});

Ext.tip.QuickTipManager.init();

Ext.override(Ext.form.field.Base,{
    initComponent:function(){
        if(this.allowBlank!==undefined && !this.allowBlank){
            if(this.fieldLabel){
                this.fieldLabel += '<font color=red>*</font>';
            }
        }
        this.callParent(arguments);
    }
});

var processAuthorize = function (options) {
    options = options || {};
    options.headers = app.ux.Utils.getAuthorizeHeader();

    var oldFailure = options.failure;
    var oldSuccess = options.success;

    options.failure = function (response, options) {
        app.ux.Utils.handle403Respones(response);
        if (oldFailure) {
            oldFailure.call(this, response, options);
        }
    };

    options.success = function (response, options) {
        // if(response.responseText.indexOf('type=Forbidden, status=403')!=-1){
        //     Ext.Msg.alert("系统提示","系统需要重新登录！");

        //     sessionStorage.removeItem("authentication_data");
        //     window.location.href = "front-login";
        //     return;
        // }
        if (oldSuccess) {
            oldSuccess.call(this, response, options);
        }
    };
    return options;
};
Ext.override(Ext.data.Connection, {
    request: function (options) {
        options = processAuthorize(options);
        this.callParent(arguments);
    }
});

Ext.application({
    name: 'imms',
    paths: {
       'app': 'js/app'
    },
    extend: 'app.Application',
    requires: [        
        'app.view.main.Main'
    ],
    mainView: 'app.view.main.Main'
});