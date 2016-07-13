Ext.define('Rhino.view.uw.benefit.ProviderForm',{
   extend: 'Ext.form.Panel',
    alias: 'widget.provider-form',
    requires: [
        'Ext.button.Button','Ext.form.field.Text'
    ],
    
    viewModel: {
        type: 'care'
    },
    
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
            xtype: 'hiddenfield',
            name: 'idProvider',
            fieldLabel: 'Label',
            bind: '{currentProvider.idProvider}'
        },
        {
            fieldLabel: 'Name',
            name: 'providerName',
            bind: '{currentProvider.providerName}'
        },
        {
            fieldLabel: 'Provider Type',
            name: 'providerType',
            bind: '{currentProvider.providerType}'
        },
        {
            fieldLabel: 'Email',
            name: 'email',
            bind: '{currentProvider.email}'
        },
        {
            fieldLabel: 'Tel.',
            name: 'tel',
            bind: '{currentProvider.tel}'
        },
        {
            fieldLabel: 'Town',
            name: 'town',
            bind: '{currentProvider.town}'
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