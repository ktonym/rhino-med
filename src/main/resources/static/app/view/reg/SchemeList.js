/**
 * Created by akipkoech on 26/10/2016.
 */
Ext.define('Rhino.view.reg.SchemeList',{
    extend: 'Ext.grid.Panel',
    alias: 'widget.scheme-list',
    reference: 'schemeList',
    cls: 'email-inbox-panel shadow-panel',
    /*viewModel: {
        type: 'scheme'
    },*/
    bind: {
        store: '{schemes}',
        selection: '{current.scheme}'
    },
    tbar: [
        {
            iconCls: 'x-fa fa-plus',
            text: 'Add',
            listeners: {
                click: 'onSchemeAdd'
            }
        },
        {
            iconCls: 'x-fa fa-edit',
            text: 'Edit',
            listeners: {
                click: 'onSchemeEdit'
            },
            bind: {
                disabled: '{!schemeList.selection}'
            }
        },
        {
            iconCls: 'x-fa fa-umbrella',
            text: 'Anniversaries',
            listeners: {
                click: 'onAnnivsClick'
            },
            bind: {
                disabled: '{!schemeList.selection}'
            }
        },
        {
            iconCls: 'x-fa fa-group',
            text: 'Members',
            listeners: {
                click: 'onSchemeMembersClick'
            },
            bind: {
                disabled: '{!schemeList.selection}'
            }
        }
    ],
    columns: [
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
        {
            renderer: Ext.util.Format.dateRenderer('d/m/Y'),
            dataIndex: 'joined',
            text: 'Join Date',
            flex: 1
        }
    ]
});