/**
 * Created by user on 04/04/2017.
 */
Ext.define('Rhino.model.uw.Quotation',{
    extend: 'Rhino.model.uw.Base',
    entityName: 'Quotation',
    idProperty: 'idQuotation',
    fields: [
        { name: 'idQuotation', type: 'int', useNull: true },
        { name: 'quotationDate', type: 'date', dateFormat: 'Ymd' },
        { name: 'sum', type: 'int', persist: false },
        { name: 'idCorporate', type: 'int', reference: 'Corporate' }
    ]
});