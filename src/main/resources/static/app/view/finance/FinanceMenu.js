Ext.define('Rhino.view.finance.FinanceMenu',{
    extend: 'Ext.menu.Menu',

    alias: 'widget.finance-menu',

    title: 'Menu',

    iconCls: 'x-fa fa-dollar',

    floating: false,

    items: [
        {
            routeId: 'invoices',
            iconCls: 'x-fa fa-list-alt',
            text: 'Invoices'
        },
        {
            routeId: 'payments',
            iconCls: 'x-fa fa-gift',
            text: 'Payments'
        },
        {
            routeId: 'vouchers',
            iconCls: 'x-fa fa-link',
            text: 'Vouchers'
        },
        {
            routeId: 'fin-reports',
            iconCls: 'x-fa fa-list-alt',
            text: 'Reports'
        }
    ]
});
