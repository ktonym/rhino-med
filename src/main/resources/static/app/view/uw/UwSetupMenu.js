Ext.define('Rhino.view.uw.UwSetupMenu',{
    extend: 'Ext.menu.Menu',
    
    alias: 'widget.uwsetupmenu',
    
    title: 'Setup',
    
    iconCls: 'x-fa fa-cogs',
    
    floating: false,
    
    items: [
        //{
        //    routeId: 'intermediary-form',//TODO create this widget
        //    iconCls: 'x-fa fa-create',
        //    text: 'Create intermediary',
        //    params: {
        //        openWindow: true,
        //        targetCfg: {},
        //        windowCfg: {
        //            title: 'Create Intermediary',
        //            maxHeight: 500,
        //            maxWidth: 500
        //        }
        //    }
        //},
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
            routeId: 'benefitlist', //TODO create this widget
            iconCls: 'x-fa fa-gift',
            text: 'Benefits'
        }
        
    ]
});