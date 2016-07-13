
Ext.define("Rhino.view.reinsurance.Reinsurance",{
    extend: "Ext.container.Container",

    requires: [
        "Rhino.view.reinsurance.ReinsuranceController",
        "Rhino.view.reinsurance.ReinsuranceModel"
    ],

    controller: "reinsurance",
    viewModel: {
        type: "reinsurance"
    },

    html: "Hello, Reinsurance World!!"
});
