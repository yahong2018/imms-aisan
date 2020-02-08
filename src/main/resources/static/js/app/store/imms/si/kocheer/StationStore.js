Ext.define("app.store.imms.si.kocheer.StationStore",{
    extend:"app.store.BaseStore",
    alias:"widget.app_store_imms_si_kocheer_StationStore",
    model:"app.model.imms.si.kocheer.StationModel",
    dao:{
        selectUrl: "api/imms/si/kocheer/station/getAll",
        deleteUrl: 'api/imms/si/kocheer/station/deleteAll',
        insertUrl: 'api/imms/si/kocheer/station/create',
        updateUrl: 'api/imms/si/kocheer/station/update',
    }
});