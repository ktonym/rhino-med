/**
 * Created by user on 22/03/2017.
 */
Ext.define('Rhino.view.reg.PolicyMemberController',{
    extend: 'Ext.app.ViewController',
    alias: 'controller.policy-member',

    onMemberPolicyAdd: function () {
        var me = this,
            vm = me.getViewModel(),
            vw = me.getView(),
            corpAnniv = vm.get('current.anniv'),
            // rec = Ext.create('Rhino.model.uw.MemberAnniv', {
            //     idCorpAnniv: corpAnniv.data.idCorpAnniv,
            //     idMember: null,
            //     memberInception: corpAnniv.inception,
            //     memberExpiry: corpAnniv.expiry
            // }),
            win, schemeId,annivId,store;
        debugger;
        win = Ext.create('Ext.window.Window',{
            title: 'Search and Add Member',
            iconCls: 'x-fa fa-binoculars',
            width: 200,
            height: 250,
            layout: 'vbox',
            listeners: {
                beforerender: function () {
                    schemeId = vm.get('current.scheme.id');
                    annivId = corpAnniv.data.idCorpAnniv;
                    store = vm.getStore('uncoveredMembers');
                    store.loadByUncovered(schemeId,annivId);
                }
            },
            items: [
                {
                    xtype: 'fieldcontainer',
                    layout: 'hbox', fieldLabel: 'Member name',
                    defaults: { flex: 1},
                    items: [
                        {
                            xtype: 'textfield',
                            fieldLabel: '',
                            hideLabel: 'true',
                            name: 'searchStr'
                        },
                        {
                            xtype: 'button',
                            text: 'Go',
                            handler: 'doSearch'
                        }
                    ]
                },
                {
                    xtype: 'grid',
                    layout: 'anchor',
                    bind: {
                        store:  '{uncoveredMembers}'
                    },
                    selectionModel: 'checkbox',
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
                    ]
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

        vw.add([win]);
        win.show();

    },

    onMassMemberPolicyAdd: function () {

    },

    onMemberPolicySave: function () {

    },

    onMemberPolicySuspend: function () {

    },

    onMemberPolicyReinstate: function () {

    },
    doLoadUncoveredMembers: function () {
        var me = this,
            vm = me.getViewModel(),
            corpAnniv = vm.get('current.anniv'),
            schemeId = vm.get('current.scheme.id'),
            annivId = corpAnniv.data.idCorpAnniv,
            store = vm.getStore('uncoveredMembers');
        store.loadByUncovered(schemeId,annivId);
    }
});