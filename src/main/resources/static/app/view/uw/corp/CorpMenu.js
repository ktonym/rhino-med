Ext.define('Rhino.view.uw.corp.CorpMenu',{
    extend: 'Ext.menu.Menu',
    
    alias: 'widget.corpmenu',
    
    viewModel: {
        type: 'corporate'
    },
    
    title: 'Schemes',
    
    iconCls: 'x-fa fa-institution',
    
    floating: false,
    
    items: [
        {
            routeId: 'corplist',
            text: 'List',
            iconCls: 'x-fa fa-list-alt'
        },
        {
            routeId: 'corp-container',
            text: 'Manage Plans',
            iconCls: 'x-fa fa-building'
        },
        {
            routeId: 'uw-reports',
            text: 'Reports',
            iconCls: 'x-fa fa-bar-chart'
        }
    ]
    
    
});