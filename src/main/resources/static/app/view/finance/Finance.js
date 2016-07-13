
Ext.define("Rhino.view.finance.Finance",{
    extend: "Ext.container.Container",

    requires: [
        "Rhino.view.finance.FinanceController",
        "Rhino.view.finance.FinanceModel"
    ],

    controller: "finance",
    viewModel: {
        type: "finance"
    },

    html: "Hello, Finance World!!"
});
