Ext.define('Rhino.view.uw.corp.CorpForm',{
    extend: 'Rhino.view.uw.Window',
    xtype: 'corp-form',

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
                },
                {
                    fieldLabel: 'PIN',
                    name: 'pin',
                    bind: '{currentCorporate.pin}'
                },
                {
                    fieldLabel: 'Email',
                    name: 'email',
                    bind: '{currentCorporate.email}'
                },
                {
                    fieldLabel: 'Tel.',
                    name: 'tel',
                    bind: '{currentCorporate.tel}'
                },
                {
                    fieldLabel: 'Postal Address',
                    name: 'postalAddress',
                    bind: '{currentCorporate.postalAddress}'
                },

                {
                    xtype: 'datefield',
                    fieldLabel: 'Date joined',
                    name: 'joined',
                    bind: '{currentCorporate.joined}'
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

        }
    ]

});