Ext.define('Rhino.model.uw.Branch',{
    extend: 'Rhino.model.uw.Base',
    entityName: 'Branch',
    fields: [
        { name: 'id', mapping: 'idBranch', type: 'int', useNull: true },
        { name: 'name', mapping: 'branchName', type: 'string' },
        { name: 'physicalAddress', type: 'string'}
    ],
    validators: {
        name: 'presence',
        physicalAddress: [
            { type: 'presence', message: 'This field is mandatory' },
            { type: 'length', min: 5 }
        ]
    }
});