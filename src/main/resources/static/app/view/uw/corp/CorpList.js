Ext.define('Rhino.view.uw.corp.CorpList',{
    extend: 'Ext.grid.Panel',
    alias: 'widget.corplist',
    reference: 'corpList',
    // session: true,
    requires:  [//'Rhino.model.uw.Corporate',
                //'Rhino.model.uw.CorpAnniv',
                //'Rhino.model.uw.AnnivSusp'
                ],//,'Rhino.model.uw.ContactPerson'],
    
    cls: 'email-inbox-panel shadow-panel',
    
    viewModel: {
        type: 'corporate'
    },

    controller: 'corporate',
    
    bind: '{corporates}',
    
    viewConfig: {
        preserveScrollOnRefresh: true,
        preserveScrollOnReload: true
    },
    
//    selModel: {
//        selType: 'checkboxmodel',
//        checkOnly: true,
//        showHeaderCheckbox: true
//    },
    
    listeners: {
        cellclick: 'onCorpListItemClick'
    },

    headerBorders: false,
    rowLines: false,
    bbar: {
        xtype: 'pagingtoolbar',
        itemId: 'pagingtoolbar',
        bind: {
            store: '{corporates}'
        },
        displayInfo: true
        //displayMsg: 'Displaying {0} to {1} of {2} records ',
        //emptyMsg: "No records to display&nbsp;"
    },

    tbar: [
        {
            iconCls: 'x-fa fa-plus',
            listeners: {
                click: 'onAddCorpBtnClick'
            }
        },
        {
            iconCls: 'x-fa fa-edit',
            listeners: {
                click: 'onEditCorpBtnClick'
            },
            bind: {
                disabled: '{!corpList.selection}'
            }
        },
        {
            iconCls: 'x-fa fa-umbrella',
            listeners: {
                click: 'onAnnivsBtnClick'
            },
            bind: {
                disabled: '{!corpList.selection}'
            }
        }
        ],

    columns: [
        //{
        //    dataIndex: 'isActive',
        //    menuDisabled: true,
        //    text: '<span class="x-fa fa-heart"></span>',
        //    width: 40,
        //    renderer: function(value){
        //        return '<span class="x-fa fa-heart'+(value ? '' : '-o') +'"></span>';
        //    }
        //},
        {

            dataIndex: 'id',
            text: 'Corp ID',
            width: 60
        },
        {
            dataIndex: 'abbreviation',
            text: 'Abbreviation',
            width: 60
        },
        {
            dataIndex: 'pin',
            text: 'PIN',
            width: 60
        },
        {
            dataIndex: 'name',
            text: 'Name',
            flex: 2
        },
        {
            dataIndex: 'email',
            text: 'Email',
            flex: 1
        },
        {
            dataIndex: 'tel',
            text: 'Telephone',
            flex: 1
        },
        //{
        //    dataIndex: 'postalAddress',
        //    text: 'Postal Address',
        //    flex: 1
        //},
        {
            xtype: 'datecolumn',
            dataIndex: 'joined',
            text: 'Join Date',
            flex: 1
        }
    ]
    
    
});