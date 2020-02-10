Ext.define('app.Application', {
    extend: 'Ext.app.Application',
    name: 'app',
    appFolder: 'js/app',

    views: [],
    controllers: [],
    stores: [],

    launch: function () {
        Ext.override(Ext.form.field.Base, {
            initComponent: function () {
                if (this.allowBlank !== undefined && !this.allowBlank) {
                    if (this.fieldLabel) {
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
    }
});