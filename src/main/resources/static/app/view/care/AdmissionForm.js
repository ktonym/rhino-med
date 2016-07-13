Ext.define('Rhino.view.care.AdmissionForm',{
    extend: 'Ext.form.Panel',
    alias: 'widget.admission-form',

    requires: [
        'Ext.button.Button','Ext.form.field.Text','Ext.form.field.Date'
    ],
    controller: 'care',

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
            xtype: 'numberfield',
            name: 'id',
            fieldLabel: 'Pre-Auth. Id',
            bind: '{currentAdmission.id}'
        },
        {
            fieldLabel: 'Member No',
            name: 'memberNo',
            bind: '{currentAdmission.memberNo}'
        },
        {
            xtype: 'textareafield',
            fieldLabel: 'Diagnosis',
            name: 'diagnosis',
            bind: '{currentAdmission.diagnosis}'
        },
        {
            xtype: 'numberfield',
            fieldLabel: 'Limit',
            name: 'limit',
            bind: '{currentAdmission.limit}'
        },
        {
            xtype: 'datefield',
            fieldLabel: 'Adm Date',
            name: 'admissionDate',
            bind: '{currentAdmission.admissionDate}'
        }
    ],
    bbar: {
        //overflowHandler: 'menu',
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
                handler: 'onSaveBtnClick'
            }

        ]
    }

});
