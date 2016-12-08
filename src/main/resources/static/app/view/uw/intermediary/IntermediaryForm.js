Ext.define('Rhino.view.uw.intermediary.IntermediaryForm',{
    extend: 'Rhino.view.uw.Window',
    xtype: 'intermediary-form',

    maxHeight: 500,
    maxWidth: 450,

    requires: [
        'Ext.button.Button','Ext.form.field.Text'
    ],
    //viewModel: {
    //    type: 'intermediary'
    //},
    //controller: 'intermediary',
    items:[
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
                labelWidth: 120,
                labelSeparator: '',
                xtype: 'textfield'
            },
            items: [
                {
                    xtype: 'hiddenfield',
                    name: 'id',
                    fieldLabel: 'Label',
                    bind: '{currentIntermediary.id}'
                },
                {
                    fieldLabel: 'Name',
                    name: 'name',
                    bind: '{currentIntermediary.name}'
                },
                {
                    fieldLabel: 'PIN',
                    name: 'pin',
                    bind: '{currentIntermediary.pin}'
                },
                {
                    xtype: 'combobox',
                    name: 'type',
                    fieldLabel: 'Type',
                    displayField: 'text',
                    valueField: 'text',
                    queryMode: 'local',
                    bind: {
                        value: '{currentIntermediary.type}',
                        store: '{intermediaryTypes}'
                    }
                },
                {
                    xtype: 'datefield',
                    fieldLabel: 'Join Date',
                    name: 'joinDate',
                    format: 'Ymd',
                    bind: '{currentIntermediary.joinDate}'
                },
                {
                    fieldLabel: 'Email',
                    name: 'email',
                    bind: '{currentIntermediary.email}'
                },
                {
                    fieldLabel: 'Tel',
                    name: 'tel',
                    bind: '{currentIntermediary.tel}'
                },
                {
                    fieldLabel: 'Postal Address',
                    name: 'postalAddress',
                    bind: '{currentIntermediary.postalAddress}'
                },
                {
                    fieldLabel: 'Street',
                    name: 'street',
                    bind: '{currentIntermediary.street}'
                },
                {
                    fieldLabel: 'Town',
                    name: 'town',
                    bind: '{currentIntermediary.town}'
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
                        handler: 'onDiscardClick'
                    },
                    {
                        xtype: 'button',
                        ui: 'soft-green',
                        text: 'Save',
                        handler: 'onSaveIntermediaryClick',
                        formBind: true
                    }

                ]
            }

        }
    ]


});