/**
 * Created by akipkoech on 16/11/2016.
 */
Ext.define('Rhino.view.reg.SchemeMembers',{
    extend: 'Ext.grid.Panel',
    alias: 'widget.scheme-members',
    reference: 'schemeMembers',
    requires: ['Rhino.view.reg.MemberModel','Rhino.view.reg.PrincipalForm'],
    // headerBorders: false,
    cls: 'email-inbox-panel shadow-panel',
    rowLines: true,

    viewModel: {
        type: 'member'
    },

    bind: {
        title: 'Scheme: {current.scheme.name}',
        store: '{corpPrincipals}',
        selection: '{current.principal}'
    },

    listeners: {
        beforerender : function () {
            var me = this,
                vm = me.getViewModel(),
                // idt = vm.get('current.scheme.id');
            // vm.getStore('corpPrincipals').loadByCorporate(idt);
                store = vm.getStore('corpPrincipals');
            console.log(store);
            // debugger;
        }
    },
    defaults: {
        xtype: 'textfield'
    },
    columns: [
        {
            dataIndex: 'familyNo',
            text: 'Family No',
            flex: 1
        },
        {
            dataIndex: 'fullName',
            text: 'Name',
            flex: 3
        },
        {
            xtype: 'datecolumn',
            dataIndex: 'dob',
            text: 'Date of Birth',
            flex: 1
        }
    ],
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
            text: 'Add',
            listeners: {
                click: 'onAddPrincipal'
            }
        },
        {
            iconCls: 'x-fa fa-edit',
            text: 'Edit',
            listeners: {
                click: 'onEditPrincipal'
            },
            bind: {
                disabled: '{!schemeMembers.selection}'
            }
        },
        {
            iconCls: 'x-fa fa-umbrella',
            text: 'Anniversaries',
            listeners: {
                click: 'onAnnivsClick'
            },
            bind: {
                disabled: '{!schemeMembers.selection}'
            }
        }
    ]
});