/**
 * Created by user on 06/04/2017.
 */
Ext.define('Rhino.model.uw.IndividualRate',{
    extend: 'Rhino.model.uw.PremiumRate',
    entityName: 'IndividualRate',
    fields: [
        { name: 'minAge', type: 'int'},
        { name: 'maxAge', type: 'int'}
    ],
    validators: {
        minAge: [
            { type: 'presence', message: 'This field is mandatory'}
        ],
        maxAge: [
            { type: 'presence', message: 'This field is mandatory'}
        ]
    }
});