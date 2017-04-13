/**
 * Created by user on 04/04/2017.
 */
Ext.define('Rhino.model.uw.QuotationItem',{
    extend: 'Rhino.model.uw.Base',
    entityName: 'QuotationItem',
    idProperty: 'idQuoteItem',
    fields: [
        { name: 'idQuoteItem', type: 'int', useNull: true },
        { name: 'idQuotation', type: 'int', reference: 'Quotation' },
        { name: 'idPremiumRate', type: 'int', reference: 'PremiumRate' },
        { name: 'quantity', type: 'int' },
        { name: 'factor', type: 'int' },
        { name: 'unitPrice', type: 'int', persist: false }
    ]
});