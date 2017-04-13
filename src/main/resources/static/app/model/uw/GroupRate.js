/**
 * Created by user on 06/04/2017.
 */
Ext.define('Rhino.model.uw.GroupRate',{
    extend: 'Rhino.model.uw.PremiumRate',
    entityName: 'GroupRate',
    fields: [
        { name: 'idCorporate', type: 'int', reference: 'Corporate'}
    ],
    validators: {
        idCorporate: [
            { type: 'presence', message: 'This field is mandatory'}
        ]
    }
});