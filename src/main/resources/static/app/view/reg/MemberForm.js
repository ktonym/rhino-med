/**
 * Created by akipkoech on 15/11/2016.
 */
Ext.define('Rhino.view.reg.MemberForm',{
    extend: 'Ext.form.Panel',
    alias: 'widget.member-form',
    requires: ['Rhino.view.reg.MemberController','Rhino.view.reg.MemberModel'],//
    controller: 'member',
    viewModel: {
        type: 'member'
    },
    cls: 'form-compose',
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    bodyPadding: 10,
    scrollable: true,

    defaults: {
        labelWidth: 120,
        labelSeparator: '',
        xtype: 'textfield'
    },
    items: [
        {
            xtype: 'hiddenfield',
            name: 'idMember',
            bind: '{current.member.idMember}'
        },
        {
            xtype: 'textfield',
            name: 'memberNo',
            bind: '{current.member.memberNo}'
        },
        {
            xtype: 'textfield',
            name: 'firstName',
            bind: '{current.member.firstName}'
        },
        {
            xtype: 'textfield',
            name: 'surname',
            bind: '{current.member.surname}'
        },
        {
            xtype: 'textfield',
            name: 'otherNames',
            bind: '{current.member.otherNames}'
        },
        {
            xtype: 'combo',
            name: 'sex',
            bind: '{current.member.sex}'
        },
        {
            xtype: 'combo',
            name: 'memberType',
            bind: '{current.member.memberType}'
        }
    ],
    bbar: {
        overflowHandler: 'menu',
        items: [

            '->',
            {
                xtype: 'button',
                ui: 'soft-red',
                text: 'Discard',
                handler: 'onDiscardMember'
            },
            {
                xtype: 'button',
                ui: 'soft-green',
                text: 'Save',
                handler: 'onSaveMember'
            }

        ]
    }
});