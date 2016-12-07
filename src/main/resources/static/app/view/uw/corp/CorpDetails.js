Ext.define('Rhino.view.uw.corp.CorpDetails',{
   extend: 'Ext.panel.Panel',
    alias: 'widget.corp-details',
    reference: 'corpDetails',
    
    requires: [
        'Ext.container.Container','Ext.layout.container.Anchor','Ext.layout.container.HBox','Ext.layout.container.VBox'],

    viewModel: {
        type: 'corporate'
    },

    bind: '{anniversaries}',

    //title: '{currentCorporate.name}', //lookout for this

    cls: 'shadow-panel',
    bodyPadding: 10,
    layout: {
        type: 'hbox',
        align: 'left'
    },

    items: [
        {
            xtype: 'dataview',
            reference: 'annivsList',
            store: 'anniversaries',
            multiselect: false,
            flex: 1,
            trackOver: true,
            overItemCls: 'x-item-over',
            emptyText: 'No anniversaries defined',
            tpl: [
                '<tpl for=".">',
                '<div class="anniv">',
                '<table style="width:100%">',
                '<tr>',
                '<td>Anniv: {anniv}</td>',
                '<td><b>Inception: </b> {inception}</td>',
                '</tr>',
                '<tr>',
                '<td><b>Expiry: </b> {expiry}</td>',
                '</tr>',
                '<tr>',
                '<td><b>Renewal: </b> {renewal}</td>',
                '</tr>',
                '</table>',
                '</div>',
                '</tpl>'
            ],
            itemSelector: 'div.anniv'
        },
        {
            xtype: 'container',
            layout: 'vbox',
            items: [
                {
                    xtype: 'panel',
                    html: 'Categories go here...'
                },
                {
                    xtype: 'panel',
                    html: 'Category benefits go here...'
                }
            ]
        }
    ],

    tbar: [
        {
            iconCls: 'x-fa fa-angle-left',
            listeners: {
                click: 'onBackBtnClick'
            }
        },
        {
            iconCls: 'x-fa fa-trash',
            listeners: {
                click: 'onDelCorpBtnClick'
            },
            toolTip: 'Delete this plan'
        },
        {
            iconCls: 'x-fa fa-print'
        },
        {
            iconCls: 'x-fa fa-edit',
            listeners: {
                click: 'onEditCorpBtnClick'
            }
        },
        {
            iconCls: 'x-fa fa-umbrella',
            listeners: {
                click: 'onAnnivsBtnClick'
            }
        },
        {
            iconCls: 'x-fa fa-user',
            listeners: {
                click: 'onMembersBtnClick'
            }
        }
    ]

});