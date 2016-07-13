Ext.define('Rhino.view.uw.Underwriting',{
    extend: 'Ext.container.Container',
    xtype: 'underwriting',
    requires: [
        'Rhino.view.uw.UwController','Rhino.view.uw.corp.CorpMenu','Rhino.view.uw.indiv.IndivMenu','Rhino.view.uw.UwSetupMenu',
        'Rhino.view.uw.corp.CorpList','Rhino.view.uw.corp.CorpController','Rhino.view.uw.corp.CorpModel'
    ],
    controller: 'underwriting',
    layout: { type:  'hbox', align: 'stretch'},
    
    margin: '20 0 0 20',
    //height: 300,
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
                    xtype: 'corpmenu',
                    listeners: {
                        click: 'onMenuClick'
                    }
                },
                {
                    xtype: 'indivmenu',
                    listeners: {
                        click: 'onMenuClick'
                    }
                },
                {
                    xtype: 'uwsetupmenu',
                    listeners: {
                        click: 'onMenuClick'
                    }
                }
            ]
    
        }, {
            xtype: 'container',
            itemId: 'contentPanel',
            margin: '0 20 20 0',
            flex: 1,
            layout: {
                type: 'anchor',
                anchor: '100%'
            }
        }
    ]
});