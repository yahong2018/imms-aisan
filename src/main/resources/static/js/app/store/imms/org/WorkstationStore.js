Ext.define("app.store.imms.org.WorkstationStore", {
    extend: "app.store.BaseStore",
    model: 'app.model.imms.org.WorkstationModel',
    alias: 'widget.imms_org_WorkstationStore',

    dao: {
        deleteUrl: 'api/imms/mes/org/workstation/deleteAll',
        insertUrl: 'api/imms/mes/org/workstation/create',
        updateUrl: 'api/imms/mes/org/workstation/update',
        selectUrl: 'api/imms/mes/org/workstation/getAll',
    },
    workshop:null,
    getStationByWorkshop: function () {
        if(this.workshop==null){
            return;
        }

        var workshopId = this.workshop.get("recordId");
        this.getProxy().url = "api/imms/mes/org/workstation/getWorkshopStations?workshopId=" + workshopId;

        this.load();
    }
});