/**
 * Created by akipkoech on 16/11/2016.
 */
Ext.define('Rhino.view.reg.SchemeMembers',{
    extend: 'Ext.grid.Panel',
    alias: 'widget.scheme-members',
    reference: 'schemeMembers',
    requires: ['Rhino.view.reg.MemberModel','Rhino.view.reg.MemberForm'],
    // headerBorders: false,
    cls: 'email-inbox-panel shadow-panel',
    rowLines: true,

    viewModel: {
        type: 'member'
    },

    bind: {
        title: 'Scheme: {current.scheme.name}',
        store: '{members}',
        selection: '{current.member}'
    },

    listeners: {
        beforerender : function () {
            var me = this,
                vm = me.getViewModel(),
                corpId = vm.get('current.scheme.id'),
                store = vm.getStore('members');
            store.loadByCorporate(corpId);
            console.info('Showing contents of store..');
            console.log(store);
        }
    },
    columns: [
        {
            dataIndex: 'memberNo',
            text: 'Member No',
            flex: 1
        },
        {
            dataIndex: 'fullName',
            text: 'Name',
            flex: 2
        },
        {
            renderer: Ext.util.Format.dateRenderer('d/m/Y'),
            dataIndex: 'dob',
            text: 'Date of Birth',
            flex: 1
        },
        {
            text: 'Principal',
            sortable: true,
            flex: 2,
            renderer: function (value, metaData, record, rowIndex, colIndex, store, view) {
                return record.getPrincipal().get('fullName');
            }
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
                click: 'onAddMember'
            }
        },
        {
            iconCls: 'x-fa fa-edit',
            text: 'Edit',
            listeners: {
                click: 'onEditMember'
            },
            bind: {
                disabled: '{!schemeMembers.selection}'
            }
        },
        {
            iconCls: 'x-fa fa-umbrella',
            text: 'Policies',
            listeners: {
                click: 'onMemberAnnivsClick'
            },
            bind: {
                disabled: '{!schemeMembers.selection}'
            }
        }
    ]
});