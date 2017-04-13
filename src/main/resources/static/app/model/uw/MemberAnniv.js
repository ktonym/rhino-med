/**
 * Created by user on 12/02/2017.
 */
Ext.define('Rhino.model.uw.MemberAnniv',{
    extend: 'Rhino.model.uw.Base',
    entityName: 'MemberAnniv',
    idProperty: 'idMemberAnniv',
    /*idProperty: function () {
       return    
    },*/
    fields: [
        { name: 'idMemberAnniv', type: 'int', useNull: true},
        { name: 'idMember', type: 'int', reference: 'Member'},
        { name: 'idCorpAnniv', type: 'int', reference: 'CorpAnniv'},
        { name: 'memberInception', type: 'date', dateFormat: 'Ymd'},
        { name: 'memberExpiry', type: 'date', dateFormat: 'Ymd'}
    ]
});