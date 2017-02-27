/**
 * Created by user on 14/02/2017.
 */
Ext.define('Rhino.model.uw.CorpBenefit', {
    extend: 'Rhino.model.uw.Base',
    entityName: 'CorpBenefit',
    idProperty: 'idCorpBenefit',
    fields: [
        { name: 'idCorpBenefit', type: 'int', useNull: true },
        { name: 'idCategory', type: 'int', reference: 'Category' },
        { name: 'benefitCode', type: 'int', reference: 'BenefitRef' },
        { name: 'upperLimit', type: 'int' },
        { name: 'memberType', type: 'string' },
        { name: 'benefitType', type: 'string' },
        { name: 'sharing', type: 'boolean' },
        { name: 'requiresPreAuth', type: 'boolean' },
        { name: 'waitingPeriod', type: 'int' },
        { name: 'idParentCorpBenefit', type: 'int', reference: 'CorpBenefit'}
    ]
});



