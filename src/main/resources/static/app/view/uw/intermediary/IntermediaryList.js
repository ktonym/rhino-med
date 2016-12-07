Ext.define('Rhino.view.uw.intermediary.IntermediaryList',{
   extend: 'Ext.grid.Panel',
    alias: 'widget.intermediarylist',
    reference: 'intermediaryList',
    session: true,
    controller: 'intermediary',
    viewModel: {
        type: 'intermediary'
    },
    bind: '{intermediaries}',

    cls: 'email-inbox-panel shadow-panel',
    plugins:[
        {
            ptype: 'rowexpander',
            rowBodyTpl:[
                '<b>PIN:</b> {pin}</br>',
                '<b>Postal Address:</b> {postalAddress}</br>',
                '<b>Street:</b> {street}, <b>Town:</b> {town}</br>'
            ]
        }
    ],
    tbar: [
        {
            iconCls: 'x-fa fa-plus',
            listeners: {
                click: 'onAddBtnClick'
            }
        },
        {
            iconCls: 'x-fa fa-edit',
            listeners: {
                click: 'onEditBtnClick'
            },
            bind: {
                disabled: '{!intermediaryList.selection}'
            }
        },
        {
            iconCls: 'x-fa fa-delete',
            listeners: {
                click: 'onDelCorpBtnClick'
            },
            bind: {
                disabled: '{!intermediaryList.selection}'
            }
        }
    ],
    columns: [

        {
            width: 50,
            dataIndex: "id",
            text: "#",
            filter: {
                type: 'numeric'
            }
        },
        {
            flex: 1,
            dataIndex: "name",
            text: "Name",
            filter: {
                type: 'string'
            }
        },
        {
            width: 100,
            dataIndex: "type",
            text: "Type",
            filter: {
                type: 'string'
            }
        },
        {
            width: 100,
            dataIndex: "tel",
            text: "Telephone",
            filter: {
                type: 'string'
            }
        },
        {
            width: 150,
            dataIndex: "email",
            text: "Email",
            filter: {
                type: 'string'
            }
        },
        {
            width: 150,
            dataIndex: "joinDate",
            format: "d-m-Y",
            text: "Join Date"
        }

    ]

    //bind: {
    //    store: '{intermediaries}'
    //}
});