/**
 * Created by user on 22/03/2017.
 */
Ext.define('Rhino.view.reg.PolicyMemberList',{
    extend: 'Ext.grid.Panel',
    alias: 'widget.policy-member-list',
    reference: 'policyMemberList',
    //requires: ['Rhino.view.reg.PolicyMemberController'],
    //controller: 'policy-member',
    viewModel: {
        type: 'reg'
    },
    rowLines: true,
    bind: {
        title: '{current.scheme.name} Policy term({current.anniv.anniv}) members',
        store: '{policyMembers}',
        selection: '{current.member-policy}'
    },

    listeners: {
        beforerender : function () {
            var me = this,
                vm = me.getViewModel(),idt;
            idt = vm.get('current.anniv.idCorpAnniv');
            vm.getStore('policyMembers').loadByCorpAnniv(idt);
        }
    },
    columns: [
        {
            dataIndex: 'memberNo',
            text: 'Member Number',
            flex: 1
        },
        {
            dataIndex: 'fullName',
            text: 'Name',
            flex: 2
        },
        {
            dataIndex: 'memberInception',
            text: 'Join',
            renderer: Ext.util.Format.dateRenderer('d/m/Y'),
            flex: 1
        },
        {
            dataIndex: 'memberExpiry',
            text: 'End',
            renderer: Ext.util.Format.dateRenderer('d/m/Y'),
            flex: 1
        }
    ],
    tbar:[
        {
            text: 'Search and Add',
            iconCls: 'x-fa fa-binoculars',
            handler: 'onMemberPolicyAdd'
        },
        {
            text: 'Suspend',
            iconCls: 'x-fa fa-lock',
            handler: 'onSuspendMemberPolicy',
            bind: {
                disabled: '{!policyMemberList.selection}'
            }
        }
    ]
});