Ext.define('Rhino.view.uw.corp.CorpAnnivForm',{

    extend: 'Rhino.view.uw.Window',
    xtype: 'corp-anniv-form',
    requires: [
        'Ext.button.Button','Ext.form.field.Text'
    ],

    maxHeight: 420,
    maxWidth: 450,

    items: [
        {
            xtype: 'form',
            reference: 'form',
            modelValidation: true,
            cls: 'form-compose',
            layout: {
                type: 'vbox',
                align: 'stretch'
            },
            bodyPadding: 10,
            scrollable: true,
            defaults: {
                //labelWidth: 120,
                labelSeparator: '',
                xtype: 'textfield'
            },
            items: [
                {
                    xtype: 'hiddenfield',
                    name: 'idCorporate',
                    bind: '{currentCorporate.id}'
                },
                {
                    fieldLabel: 'Name',
                    name: 'name',
                    bind: '{currentCorporate.name}'
                },
                {
                    fieldLabel: 'Abbreviation',
                    name: 'abbreviation',
                    bind: '{currentCorporate.abbreviation}'
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
                        handler: 'onSaveCorpClick',
                        formBind: true
                    }

                ]
            }
        }]

});
