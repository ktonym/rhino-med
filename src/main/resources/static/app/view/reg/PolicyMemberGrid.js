/**
 * Created by user on 26/03/2017.
 */
Ext.define('Rhino.view.reg.PolicyMemberGrid',{
    extend: 'Ext.grid.Panel',
    alias: 'widget.policy-member-grid',
    requires: ['Rhino.view.reg.PolicyMemberController'],
    controller: 'policy-member',
    viewModel: {
        type: 'reg'
    },
    bind: {
        store:  '{uncoveredMembers}'
    },
    selectionModel: 'checkbox',
    listeners:{
        beforerender: 'doLoadUncoveredMembers'
    },
    columns: [
        {
            dataIndex: 'fullName',
            text: 'Name',
            flex: 2
        },
        {
            dataIndex: 'memberNo',
            text: 'Member No',
            flex: 1
        }
    ],
    bbar: [
        '->',
        {
            xtype: 'button',
            ui: 'soft-red',
            text: 'Discard',
            handler: 'onAddMembersCancel'
        },
        {
            xtype: 'button',
            ui: 'soft-green',
            text: 'Save',
            handler: 'onAddMembersDone'
        }
    ]
});