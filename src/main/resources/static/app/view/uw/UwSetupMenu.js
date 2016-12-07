Ext.define('Rhino.view.uw.UwSetupMenu',{
    extend: 'Ext.menu.Menu',
    
    alias: 'widget.uwsetupmenu',
    
    title: 'Setup',
    
    iconCls: 'x-fa fa-cogs',
    
    floating: false,
    
    items: [
        {
            routeId: 'intermediarylist',
            iconCls: 'x-fa fa-list-alt',
            text: 'Intermediaries'
        },
        //{
        //    routeId: 'benefit-form',
        //    iconCls: 'x-fa fa-create',
        //    text: 'Add benefit',
        //    params: {
        //        openWindow: true,
        //        targetCfg: {
        //
        //        },
        //        windowCfg: {
        //            title: 'Create Benefit',
        //            maxHeight: 250,
        //            maxWidth: 500
        //        }
        //    }
        //},
        {
            routeId: 'benefit-ref-list',
            iconCls: 'x-fa fa-gift',
            text: 'Benefits'
        },
        {
            routeId: 'branches',
            iconCls: 'x-fa fa-link',
            text: 'Branches'
        }
        
    ]
});