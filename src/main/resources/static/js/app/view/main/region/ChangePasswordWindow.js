Ext.define('app.view.main.region.ChangePasswordWindow', {
    extend: 'Ext.window.Window',
    requires: ['Ext.form.*'],
    alias: 'widget.changePasswordWindow',

    title: '密码更改',
    bodyPadding: 5,
    width: 300,
    modal: true,
    closeAction: 'destroy',
    items: [{
        xtype: 'form',
        defaults: {
            // labelAlign: 'right',
            labelWidth: 90,
        },
        items: [{
            xtype: 'textfield',
            fieldLabel: '旧密码',
            width: 270,
            allowBlank: false,
            name: 'old',
            inputType: 'password',
        },
            {
                xtype: 'textfield',
                fieldLabel: '新密码',
                width: 270,
                allowBlank: false,
                name: 'pwd1',
                inputType: 'password',
            },
            {
                xtype: 'textfield',
                fieldLabel: '确认新密码',
                width: 270,
                allowBlank: false,
                name: 'pwd2',
                inputType: 'password',
            }
        ]
    }],
    buttons: [
        '->',
        {
            text: '更改',
            handler: function () {
                var me = this.up('changePasswordWindow');
                var form = me.down('form').getForm();
                if (!form.isValid()) {
                    return;
                }
                var record = form.getValues();
                var url = 'api/imms/admin/systemUser/changeCurrentUserPassword';
                app.ux.Utils.ajaxRequest({
                    url: url,
                    jsonData: record,
                    method: 'POST',
                    successCallback: function (result, response, opts) {
                        var msg = "密码更改出现未知错误：" + result;
                        var changed=false;
                        if (result.success && result.data === 1) {
                            msg = '已成功更改密码！';
                            changed=true;
                        } else if (result.data === -1) {
                            msg = '二次输入的新密码不一致！';
                        } else if (result.data === -2) {
                            msg = '旧密码错误！';
                        }

                        Ext.MessageBox.show({
                            title: '系统提示',
                            msg: msg,
                            buttons: Ext.MessageBox.OK,
                            icon: Ext.MessageBox.INFO,
                            fn: function (btn, text) {
                                if (changed) {
                                    me.close();
                                }
                            }
                        });
                    },
                    handleFailure: function (response, opts) {
                        Ext.MessageBox.show({
                            title: '系统提示',
                            msg: response.responseText,
                            buttons: Ext.MessageBox.OK,
                            icon: Ext.MessageBox.ERROR
                        });

                        me.close();
                    }
                });
            }
        }, {
            text: '取消',
            handler: function () {
                var me = this.up('changePasswordWindow');

                me.close();
            }
        }
    ]
});