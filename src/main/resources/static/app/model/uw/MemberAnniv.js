/**
 * Created by user on 12/02/2017.
 */
Ext.define('Rhino.model.uw.MemberAnniv',{
    extend: 'Rhino.model.uw.Base',
    entityName: 'MemberAnniv',
    /*idProperty: function () {
       return    
    },*/
    fields: [
        { name: 'idMember', type: 'int', reference: 'Member'},
        { name: 'idCorpAnniv', type: 'int', reference: 'Anniv'},
        { name: 'inception', type: 'date', dateFormat: 'Ymd'},
        { name: 'expiry', type: 'date', dateFormat: 'Ymd'}
    ]
});