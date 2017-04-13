/**
 * Created by user on 06/04/2017.
 */
Ext.define('Rhino.model.uw.BaseRate',{
    extend: 'Rhino.model.uw.Base',
    entityName: 'BaseRate',
    idProperty: 'idPremiumRate',
    requires: ['Rhino.fields.RateType'],
    fields: [
        { name: 'idPremiumRate', type: 'int', useNull:true },
        { name: 'premiumType', type: 'ratetype' },
        { name: 'upperLimit', type: 'int' },
        { name: 'premium', type: 'int' },
        { name: 'familySize', type: 'string' },
        { name: 'benefitCode', type: 'int', reference: 'BenefitRef' }
    ],
    validators: {
        premiumType: [ { type: 'presence', message: 'This field is mandatory'}],
        upperLimit:  [ { type: 'presence', message: 'This field is mandatory'}],
        premium:     [ { type: 'presence', message: 'This field is mandatory'}],
        familySize:  [ { type: 'presence', message: 'This field is mandatory'}],
        benefitCode: [ { type: 'presence', message: 'This field is mandatory'}]
    }
});