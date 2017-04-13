/**
 * Created by akipkoech on 26/10/2016.
 */
Ext.define('Rhino.view.reg.SchemeAnnivList',{
    extend: 'Ext.grid.Panel',
    alias: 'widget.scheme-anniv-list',
    reference: 'schemeAnnivList',
    // headerBorders: false,
    //requires: ['Rhino.view.reg.SchemeAnnivModel'],
    rowLines: true,

    viewModel: {
        type: 'reg'
    },

    bind: {
        title: 'Scheme: {current.scheme.name}',
        store: '{terms}',
        selection: '{current.anniv}'
    },

    listeners: {
        beforerender : function () {
            var me = this,
                vm = me.getViewModel(),idt;
            debugger;
                idt = vm.get('current.scheme.id');
            vm.getStore('terms').loadByCorporate(idt);
        }
    },

    tbar: [
        {
            iconCls: 'x-fa fa-angle-left',
            text: 'Back',
            listeners: {
                click: 'onBackHome'
            }
        },
        {
            iconCls: 'x-fa fa-plus',
            text: 'Renew',
            listeners:  {
                click: 'onAnnivAdd'
            }

        },
        {
            iconCls: 'x-fa fa-edit',
            text: 'Edit',
            listeners: {
                click: 'onAnnivEdit'
            },
            bind: {
                disabled: '{!schemeAnnivList.selection}'
            }
        },
        {
            iconCls: 'x-fa fa-cubes',
            text: 'Categories',
            listeners: {
                click: 'onCatDetailsClick'
            },
            bind: {
                disabled: '{!schemeAnnivList.selection}'
            }
        },
        {
            iconCls: 'x-fa fa-group',
            text: 'Members',
            listeners: {
                click: 'onPolicyMembersClick'
            },
            bind: {
                disabled: '{!schemeAnnivList.selection}'
            }
        }
    ],

    defaults: {
        xtype: 'datecolumn'
    },

    columns: [
        {
            dataIndex: 'anniv',
            text: 'Term',
            width: 50
        },
        {
            dataIndex: 'inception',
            text: 'Inception',
            flex: 1,
            renderer: Ext.util.Format.dateRenderer('d/m/Y')
        },
        {
            dataIndex: 'expiry',
            text: 'Expiry',
            flex: 1,
            renderer: Ext.util.Format.dateRenderer('d/m/Y')
        },
        {
            dataIndex: 'renewalDate',
            text: 'Renewal',
            flex: 1,
            renderer: Ext.util.Format.dateRenderer('d/m/Y')
        }
    ]

});