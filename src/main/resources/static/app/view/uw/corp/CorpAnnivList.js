Ext.define('Rhino.view.uw.corp.CorpAnnivList',{
    extend: 'Ext.grid.Panel',
    xtype: 'corp-anniv-list',
    reference: 'corpAnnivList',

    headerBorders: false,
    rowLines: false,

    //viewModel: {
    //    type: 'corporate'
    //},
    controller: 'corporate',

    bind:  '{anniversaries}',
    //store: 'CorpAnniv',

    //listeners: {
    //    beforerender : 'beforeAnnivListRender'
    //},

    defaults: {
      xtype: 'datecolumn'
    },

    tbar: [
        {
            iconCls: 'x-fa fa-angle-left',
            listeners: {
                click: 'onBack2CorpDtlsBtnClick'
            }
        }

    ],

    columns: [
        {
            xtype: 'numbercolumn',
            dataIndex: 'idCorporate',
            text: 'IdCorporate'
        },
        {
            xtype: 'numbercolumn',
            dataIndex: 'anniv',
            text: 'Anniversary',
            width: 50
        },
        {
            dataIndex: 'inception',
            text: 'Inception',
            format: 'Y-m-d',
            flex: 1
        },
        {
            dataIndex: 'expiry',
            text: 'Expiry',
            flex: 1
        },
        {
            dataIndex: 'renewalDate',
            text: 'Renewal',
            flex: 1
        }
    ]

});