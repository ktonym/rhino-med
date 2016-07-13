Ext.define('Rhino.view.care.ProviderMenu',{
    extend: 'Ext.menu.Menu',
    
    alias: 'widget.providermenu',
    
    title: 'Providers',
    
    iconCls: 'x-fa fa-hospital',
    
    floating: false,
    
    items: [
        {
            routeId: 'provider-form',
            iconCls: 'x-fa fa-add',
            text: 'New',
            params: 
            {
                openWindow: true,
                targetCfg: {},
                windowCfg: 
                {
                    title: 'Create Provider',
                    minWidth: 450,
                    minHeight: 500
                }
            }
        },
        {
            routeId: 'provider-list',
            iconCls: 'x-fa fa-list-alt',
            text: 'List'
        }
    ]
});