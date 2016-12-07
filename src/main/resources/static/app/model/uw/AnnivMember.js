/**
 * Created by akipkoech on 14/11/2016.
 */
Ext.define('Rhino.model.uw.AnnivMember',{
    extend: 'Rhino.model.uw.Base',
    entityName: 'AnnivMember',
    fields: [
        { name: 'id', mapping: 'idBranch', type: 'int', useNull: true },
        { name: 'name', mapping: 'branchName', type: 'string' },
        { name: 'physicalAddress', type: 'string'}
    ]
});