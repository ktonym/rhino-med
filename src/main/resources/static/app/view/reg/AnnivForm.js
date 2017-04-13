/**
 * Created by akipkoech on 27/10/2016.
 */
Ext.define('Rhino.view.reg.AnnivForm',{
    extend: 'Ext.form.Panel',
    alias: 'widget.anniv-form',
    requires: ['Rhino.view.reg.AnnivController'/*,'Rhino.view.reg.SchemeModel'*/],
    controller: 'anniv',
    /*viewModel: {
        type : 'scheme-anniv'
    },*/
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
        afterrender: 'afterAnnivFormRender'
    },

    items: [
        {
            xtype: 'hiddenfield',
            name: 'idCorpAnniv',
            bind: '{current.anniv.idCorpAnniv}'
        },
        {
            xtype: 'hiddenfield',
            name: 'idCorporate',
            bind: '{current.anniv.idCorporate}'
        },
        {
            xtype: 'numberfield',
            fieldLabel: 'Anniversary',
            name: 'anniv',
            bind: '{current.anniv.anniv}'
        },
        {
            xtype: 'combo',
            fieldLabel: 'Intermediary',
            name: 'intermediary',
            bind: {
                store: '{intermediaries}',
                value: '{current.anniv.intermediaryId}'//,
                // selection: '{current.anniv.intermediary}'
            },
            valueField: 'idIntermediary',
            displayField: 'name',
            queryMode: 'local'
           // bind: '{current.anniv.intermediary}'
        },
        {
            xtype: 'datefield',
            fieldLabel: 'Inception',
            name: 'inception',
            bind: '{current.anniv.inception}'
        },
        {
            xtype: 'datefield',
            fieldLabel: 'Expiry',
            name: 'expiry',
            bind: '{current.anniv.expiry}'
        },
        {
            xtype: 'datefield',
            fieldLabel: 'Renewal',
            name: 'renewalDate',
            bind: '{current.anniv.renewalDate}'
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
                handler: 'onDiscardAnniv'
            },
            {
                xtype: 'button',
                ui: 'soft-green',
                text: 'Save',
                handler: 'onSaveAnniv'
            }

        ]
    }
});