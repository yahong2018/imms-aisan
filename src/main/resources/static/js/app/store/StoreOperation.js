Ext.define("app.store.StoreOperation", {
    requires: ['app.ux.MapDataMeta'],

    urlMeta: null,
    fixedFilter: [],
    customerFilter: [],

    getSelectUrl: function () {
        return this.getUrl("URL_SELECT");
    },
    setSelectUrl: function (url, isAbsolute) {
        this.setUrl("URL_SELECT", url, isAbsolute);
    },
    getUpdateUrl: function () {
        return this.getUrl("URL_UPDATE");
    },
    setUpdateUrl: function (url, isAbsolute) {
        this.setUrl("URL_UPDATE", url, isAbsolute)
    },
    getDeleteUrl: function () {
        return this.getUrl("URL_DELETE");
    },
    setDeleteUrl: function (url, isAbsolute) {
        this.setUrl("URL_DELETE", url, isAbsolute);
    },
    getInsertUrl: function () {
        return this.getUrl("URL_INSERT");
    },
    setInsertUrl: function (url, isAbsolute) {
        this.setUrl("URL_INSERT", url, isAbsolute);
    },
    getUrl: function (key) {
        return this.urlMeta.get(key);
    },
    setUrl: function (key, url, isAbsolute) {
        // if (!isAbsolute) {
        //     url = url;
        // }
        // url = "api/" + url;
        this.urlMeta.put(key, url);
    },

    initUrlMeta: function (config) {
        this.urlMeta = Ext.create('app.ux.MapDataMeta');
        if (!this.dao) {
            this.dao = config.dao;
        }
        if (this.dao) {
            this.setSelectUrl(this.dao.selectUrl);
            this.setUpdateUrl(this.dao.updateUrl);
            this.setDeleteUrl(this.dao.deleteUrl);
            this.setInsertUrl(this.dao.insertUrl);
        }
    },

    clearFixedFilter: function () {
        this.fixedFilter = [];
    },

    addFixedFilter: function (filter) {
        this.fixedFilter.push(filter);
    },

    clearCustomFilter: function () {
        this.customerFilter = [];
    },

    addCustomFilter: function (filter) {
        this.customerFilter.push(filter);
    },

    buildFilterUrl: function () {
        if (this.getProxy().DefaultUrl == null) {
            this.getProxy().DefaultUrl = this.getProxy().url;
        }
        var url = this.getProxy().DefaultUrl;
        var allFilters = this.customerFilter.concat(this.fixedFilter);
        if (allFilters.length == 0) {
            this.getProxy().url = url;
            return;
        }
        var filter = JSON.stringify(allFilters);
        if (url.indexOf("?") == -1) {
            url += "?";
        } else {
            url += "&";
        }
        url = url + "filterExpr=" + Ext.util.Base64.encode(filter);
        this.getProxy().url = url;
    },
    defaultException:function (self, response, operation, eOpts) {
        app.ux.Utils.handle403Respones(response);
        Ext.Msg.alert("系统错误", response.responseText);
    },
    getDefaultProxy:function () {
        return  {
            type: 'ajax',
            url: this.getSelectUrl(),
            headers: app.ux.Utils.getAuthorizeHeader(),
            exception: this.defaultException
        };
    }

});