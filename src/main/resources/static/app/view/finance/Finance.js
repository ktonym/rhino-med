
Ext.define("Rhino.view.finance.Finance",{
    extend: "Ext.container.Container",

    requires: [
        "Rhino.view.finance.FinanceController", "Rhino.view.finance.invoice.Invoices"

    ],

    controller: "finance",

    layout: { type:  'hbox', align: 'stretch'},

    margin: '20 0 0 20',

    items: [
        {
            xtype: 'container',
            itemId: 'navigationPanel',
            layout: {
                type: 'vbox',
                align: 'stretch'
            },
            width: '30%',
            minWidth: 180,
            maxWidth: 240,

            defaults: {
                cls: 'navigation-uw',
                margin: '0 20 20 0'
            },

            items: [
                {
                    xtype: 'finance-menu',
                    listeners: {
                        click: 'onMenuClick'
                    }
                }
            ]

        }, {
            xtype: 'container',
            itemId: 'contentPanel',
            reference: 'contentPanel',
            margin: '0 20 20 0',
            flex: 1,
            layout: {
                type: 'anchor',
                anchor: '100%'
            }
        }
    ]
});
