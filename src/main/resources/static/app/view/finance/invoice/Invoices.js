Ext.define('Rhino.view.finance.invoice.Invoices',{
    extend: 'Ext.tab.Panel',
    alias: 'widget.invoices',
    requires: ['Rhino.view.finance.invoice.PremInvoice','Rhino.view.finance.invoice.FundInvoice'],
    controller: 'invoice',
    viewModel: {
        type: 'invoice'
    },
    items: [
        {
            xtype: 'prem-invoice'
        }
    ]

});