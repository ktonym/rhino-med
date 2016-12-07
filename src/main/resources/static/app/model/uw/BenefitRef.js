Ext.define('Rhino.model.uw.BenefitRef',{
    extend: 'Rhino.model.uw.Base',
    entityName: 'BenefitRef',
    idProperty: 'benefitCode',
    identifier: 'negative',
    fields: [
        { name: 'benefitCode', type: 'int', useNull: true },
        { name: 'benefitName', type: 'string' },
        { name: 'description', type: 'string'}
    ],
    validators: {
        benefitName: [
            { type: 'length', min: 3},
            { type: 'presence', message: 'This field is mandatory'}
        ],
        description: 'presence'
    }
});
