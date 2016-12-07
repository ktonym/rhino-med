Ext.define('Rhino.store.NavigationTree',{
    extend: 'Ext.data.TreeStore',
    
    storeId: 'NavigationTree',
    root: {
        expanded: true,
        children: [
            {
                text: 'My Tickets',
                view: 'ticket.Ticket',
                leaf: true,
                iconCls: 'x-fa fa-tasks',
                routeId: 'tickets'
            },
            {
                text: 'Dashboard',
                view: 'dashboard.Dashboard',
                leaf: true,
                iconCls: 'x-fa fa-dashboard',
                //iconCls: 'right-icon new-icon x-fa fa-desktop',
                routeId: 'dashboard'
            },
            {
                text: 'Registration',
                view: 'reg.Registration',
                iconCls: 'right-icon x-fa fa-briefcase',
                leaf: true,
                routeId: 'registration'
            },
            {
                text:   'Underwriting',
                view:   'uw.Underwriting',
                iconCls: 'x-fa fa-book',
                //iconCls: 'right-icon hot-icon x-fa fa-book ',
                leaf:   true,
                routeId: 'underwriting'
            },
            {
                text: 'Finance',
                view: 'finance.Finance',
                iconCls: 'x-fa fa-money',
                leaf: true,
                routeId: 'finance'
            },
            {
                text: 'Reinsurance',
                view: 'reinsurance.Reinsurance',
                iconCls: 'x-fa fa-wrench',
                //iconCls: 'right-icon new-icon x-fa fa-wrench',
                leaf: true,
                routeId: 'reinsurance'
            },
            {
                text: 'Care',
                view: 'care.Care',
                iconCls: 'x-fa fa-medkit',
                leaf: true,
                routeId: 'care'
            },
            {
                text: 'Claims',
                view: 'claims.Claims',
                iconCls: 'x-fa fa-database',
                leaf: true,
                routeId: 'claims'
            }
        ]
    },
    fields: [
        {
            name: 'text'
        }
    ]

});