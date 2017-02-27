/**
 * Created by akipkoech on 26/10/2016.
 */
Ext.define('Rhino.view.reg.SchemeAnnivList',{
    extend: 'Ext.grid.Panel',
    alias: 'widget.scheme-anniv-list',
    reference: 'schemeAnnivList',
    // headerBorders: false,
    rowLines: true,

    viewModel: {
        type: 'scheme'
    },

    bind: {
        title: 'Scheme: {current.scheme.name}',
        store: '{anniversaries}',
        selection: '{current.anniv}'
    },

    listeners: {
        beforerender : function () {
            var me = this,
                vm = me.getViewModel(),
                idt = vm.get('current.scheme.id');
            console.log(vm.getStore('anniversaries').loadByCorporate(idt));
        }
    },

    defaults: {
        xtype: 'datecolumn'
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
            iconCls: 'x-fa  fa-object-group',
            text: 'Categories',
            listeners: {
                click: 'onCatDetailsClick'
            },
            bind: {
                disabled: '{!schemeAnnivList.selection}'
            }
        },
        {
            iconCls: 'x-fa  fa-group',
            text: 'Members',
            listeners: {
                click: 'onMemberDetailsClick'
            },
            bind: {
                disabled: '{!schemeAnnivList.selection}'
            }
        }
    ],

    columns: [
        /*{
            xtype: 'numbercolumn',
            dataIndex: 'idCorporate',
            text: 'IdCorporate'
        },*/
        {
            dataIndex: 'anniv',
            text: 'Anniversary',
            width: 50
        },
        {
            dataIndex: 'inception',
            text: 'Inception',
            renderer: Ext.util.Format.dateRenderer('d/m/Y'),
            flex: 1
        },
        {
            dataIndex: 'expiry',
            text: 'Expiry',
            renderer: Ext.util.Format.dateRenderer('d/m/Y'),
            flex: 1
        },
        {
            dataIndex: 'renewalDate',
            text: 'Renewal',
            renderer: Ext.util.Format.dateRenderer('d/m/Y'),
            flex: 1
        }
    ]

});