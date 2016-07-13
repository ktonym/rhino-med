Ext.define('Rhino.view.uw.benefit.BenefitForm',{
   extend: 'Ext.form.Panel',
    alias: 'widget.benefit-form',
    requires: [
        'Ext.button.Button','Ext.form.field.Text'
    ],
    
    viewModel: {
        type: 'benefitref'
    },
    
    controller: 'benefitref',
    
    //TODO define this class on template===>email-compose
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
            name: 'benefitCode',
            fieldLabel: 'Label',
            bind: '{currentBenefit.benefitCode}'
        },
        {
            fieldLabel: 'Benefit Name',
            name: 'benefitName',
            bind: '{currentBenefit.benefitName}'
        },
        {
            xtype: 'textareafield',
            fieldLabel: 'Description',
            name: 'description',
            bind: '{currentBenefit.description}'
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
                handler: 'onSaveBtnClick'
            }            
            
        ]
    }
});