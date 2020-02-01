Ext.define("app.model.imms.mfc.ProductSummaryModel", {
    extend: "Ext.data.Model",
    requires: ["app.ux.ZhxhDate"],
    fields: [
        { name: "productDate", type: "zhxhDate", dateFormat: 'Y-m-d' },
        { name: "productionId", type: "int" },
        { name: "productionCode", type: "string" },
        { name: "productionName", type: "string" },
        { name: "workshopId", type: "int" },
        { name: "workshopCode", type: "string" },
        { name: "workshopName", type: "string" },

        { name: "qtyGood0", type: "int" },
        { name: "qtyDefect0", type: "int" },
        {
            name: "qtyTotal0", type: "int", calculate: function (data) {
                return data.qtyGood0 + data.qtyDefect0;
            }
        },
        {
            name: "rateGood0", type: "number", calculate: function (data) {
                var good = data.qtyGood0;
                if (good == 0) {
                    return 0;
                }

                return good / (data.qtyGood0 + data.qtyDefect0);
            }
        },
        {
            name: "rateDefect0", type: "number", calculate: function (data) {
                var defect = data.qtyDefect0;
                if (defect == 0) {
                    return 0;
                }

                return defect / (data.qtyGood0 + data.qtyDefect0);
            }
        },


        { name: "qtyGood1", type: "int" },
        { name: "qtyDefect1", type: "int" },
        {
            name: "qtyTotal1", type: "int", calculate: function (data) {
                return data.qtyGood1 + data.qtyDefect1;
            }
        },
        {
            name: "rateGood1", type: "number", calculate: function (data) {
                var good = data.qtyGood1;
                if (good == 0) {
                    return 0;
                }

                return good / (data.qtyGood1 + data.qtyDefect1);
            }
        }, {
            name: "rateDefect1", type: "number", calculate: function (data) {
                var defect = data.qtyDefect1;
                if (defect == 0) {
                    return 0;
                }
                return defect / (data.qtyGood1 + data.qtyDefect1);
            }
        },

        {
            name: "qtyTotal", type: "int", calculate: function (data) {
                return data.qtyGood1 + data.qtyDefect1 + data.qtyGood0 + data.qtyDefect0;
            }
        },

        {
            name: "qtyGood", type: "int", calculate: function (data) {
                return data.qtyGood1 + data.qtyGood0;
            }
        },

        {
            name: "qtyDefect", type: "int", calculate: function (data) {
                return data.qtyDefect1 + data.qtyDefect0;
            }
        },
        {
            name: "rateDefect", type: "number", calculate: function (data) {
                var defect = data.qtyDefect1 + data.qtyDefect0;
                if (defect == 0) {
                    return 0;
                }

                return defect / (data.qtyGood1 + data.qtyDefect1 + data.qtyGood0 + data.qtyDefect0);
            }
        },
        {
            name: "rateGood", type: "number", calculate: function (data) {
                var good = data.qtyGood1 + data.qtyGood0;
                if (good == 0) {
                    return 0;
                }

                return good / (data.qtyGood1 + data.qtyDefect1 + data.qtyGood0 + data.qtyDefect0);
            }
        },
    ]
});