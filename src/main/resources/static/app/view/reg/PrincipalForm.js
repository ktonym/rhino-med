/**
 * Created by akipkoech on 15/11/2016.
 */
Ext.define('Rhino.view.reg.PrincipalForm',{
    extend: 'Ext.form.Panel',
    alias: 'widget.principal-form',
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

    listeners: {
        afterrender: 'afterPrincipalFormRender'
    },

    items: [
        {
            xtype: 'hiddenfield',
            name: 'idCorporate',
            bind: '{current.principal.idCorporate}'
        },
        {
            xtype: 'hiddenfield',
            name: 'familyNo',
            bind: '{current.principal.familyNo}'
        },
        {
            xtype: 'textfield',
            name: 'firstName',
            fieldLabel: 'First Name',
            bind: '{current.principal.firstName}'
        },
        {
            xtype: 'textfield',
            name: 'surname',
            fieldLabel: 'Surname',
            bind: '{current.principal.surname}'
        },
        {
            xtype: 'textfield',
            name: 'otherNames',
            fieldLabel: 'Other Name(s)',
            bind: '{current.principal.otherNames}'
        },
        {
            xtype: 'datefield',
            name: 'dob',
            fieldLabel: 'DOB',
            bind: '{current.principal.dob}'
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
                handler: 'onDiscardPrincipal'
            },
            {
                xtype: 'button',
                ui: 'soft-green',
                text: 'Save',
                handler: 'onSavePrincipal'
            }

        ]
    }
});