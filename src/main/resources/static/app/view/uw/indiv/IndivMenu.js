Ext.define('Rhino.view.uw.indiv.IndivMenu',{
    extend: 'Ext.menu.Menu',
    
    alias: 'widget.indivmenu',
    
    viewModel: {
        type: 'uwmenu'
    },
    
    title: 'Personal Plans',
    
    iconCls: 'x-fa fa-user-md',
    
    floating: false,
    
    items: [
        {
            routeId: 'indiv-form',
            params: {
                openWindow: true,
                targetCfg: {
                    //additional configs
                },
                windowCfg: {
                    title: 'Add Individual Client'
                }
            },
            iconCls: 'x-fa fa-add',
            text: 'New'
        },
        {
            routeId: 'indiv-panel',
            text: 'Home',
            iconCls: 'x-fa fa-home'
        }
    ]
    
});