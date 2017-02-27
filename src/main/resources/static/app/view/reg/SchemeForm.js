/**
 * Created by akipkoech on 10/11/2016.
 */
Ext.define('Rhino.view.reg.SchemeForm',{
    extend: 'Ext.form.Panel',
    alias: 'widget.scheme-form',
    controller: 'scheme',
    requires: [
        'Rhino.view.reg.SchemeController','Rhino.view.reg.SchemeModel' //,'Ext.button.Button','Ext.form.field.Text'
    ],
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
        afterrender: 'afterSchemeFormRender'
    },

    items: [
        {
            xtype: 'hiddenfield',
            name: 'idCorporate',
            bind: '{current.scheme.id}'
        },
        {
            fieldLabel: 'Name',
            name: 'name',
            bind: '{current.scheme.name}'
        },
        {
            fieldLabel: 'Abbreviation',
            name: 'abbreviation',
            bind: '{current.scheme.abbreviation}'
        },
        {
            fieldLabel: 'PIN',
            name: 'pin',
            bind: '{current.scheme.pin}'
        },
        {
            fieldLabel: 'Email',
            name: 'email',
            bind: '{current.scheme.email}'
        },
        {
            fieldLabel: 'Tel.',
            name: 'tel',
            bind: '{current.scheme.tel}'
        },
        {
            fieldLabel: 'Postal Address',
            name: 'postalAddress',
            bind: '{current.scheme.postalAddress}'
        },

        {
            xtype: 'datefield',
            fieldLabel: 'Date joined',
            name: 'joined',
            bind: '{current.scheme.joined}'
        },
        {
            xtype: 'combo',
            fieldLabel: 'Plan Type',
            name: 'planType',
            displayField: 'text',
            valueField: 'text',
            queryMode : 'local',
            bind: {
                store: '{planTypes}',
                value: '{current.scheme.planType}'
            }
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
                handler: 'onCancel'
            },
            {
                xtype: 'button',
                ui: 'soft-green',
                text: 'Save',
                handler: 'onSaveScheme',
                formBind: true
            }

        ]
    }

});