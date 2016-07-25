Ext.define('Rhino.view.uw.corp.CorpMemberForm',{

    extend: 'Rhino.view.uw.Window',
    xtype: 'corp-member-form',

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
                    name: 'idMember',
                    bind: '{currentMember.id}'
                },
                {
                    fieldLabel: 'Member No',
                    name: 'memberNo',
                    bind: '{currentMember.memberNo}'
                },
                {
                    fieldLabel: 'First Name',
                    name: 'firstName',
                    bind: '{currentMember.firstName}'
                },
                {
                    fieldLabel: 'Surname',
                    name: 'surname',
                    bind: '{currentMember.surname}'
                },
                {
                    fieldLabel: 'Other Name(s)',
                    name: 'otherNames',
                    bind: '{currentMember.otherNames}'
                },
                {
                    fieldLabel: 'Sex',
                    name: 'sex',
                    bind: '{currentMember.sex}'
                },
                {
                    xtype: 'datefield',
                    fieldLabel: 'Date of birth',
                    name: 'dob',
                    bind: '{currentMember.dob}'
                },
                {
                    xtype: 'combobox',
                    fieldLabel: 'Principal',
                    name: 'principal',
                    displayField: 'currentMember.principal',
                    valueField: 'currentMember.idPrincipal',
                    queryMode: 'local',
                    bind: {
                        value: '{currentMember.idPrincipal}',
                        store: '{principals}'
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
                        handler: 'onSaveMemberClick',
                        formBind: true
                    }

                ]
            }

        }
    ]

});
