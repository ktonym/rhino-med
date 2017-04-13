/**
 * Created by akipkoech on 26/10/2016.
 */
Ext.define('Rhino.view.reg.Registration',{
    extend: 'Ext.container.Container',
    alias: 'widget.registration',
    itemId: 'registration',
    reference: 'registration',
    requires: ['Rhino.view.reg.RegMenu','Rhino.view.reg.RegModel','Rhino.view.reg.RegController',
                'Rhino.view.reg.SchemeList','Rhino.view.reg.SchemeAnnivList','Rhino.view.reg.SchemeMembers',
                'Rhino.view.reg.CatDetails','Rhino.store.CorpAnniv','Rhino.view.reg.PolicyMemberGrid'
    ],
    controller: 'registration',
    viewModel: 'reg',
    /**
     * not sure this config fixes anything...
     */
    session: {
        schema: 'uwSchema'
    },
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
                    xtype: 'regmenu',
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
