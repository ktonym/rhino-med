
Ext.define("Rhino.view.claims.Claims",{
    extend: "Ext.container.Container",
    xtype: "claims",
    requires: [
        "Rhino.view.claims.ClaimsController",
        "Rhino.view.claims.ClaimsModel"
    ],

    controller: "claims",
    viewModel: {
        type: "claims"
    },

    html: "Hello, World!!"
});
