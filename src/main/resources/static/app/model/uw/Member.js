Ext.define('Rhino.model.uw.Member', {
    extend: 'Rhino.model.uw.Base',
    idProperty: 'idMember',
    
    fields: [
        { name: 'idMember', type: 'int', useNull: true },
        { name: 'memberNo', type: 'string' },
        { name: 'firstName', type: 'string' },
        { name: 'surname', type: 'string' },
        { name: 'otherNames', type: 'string' },
        { name: 'sex', type: 'gender' },
        { name: 'dob', type: 'date', dateFormat: 'Ymd' },
        { name: 'fullName', type: 'string', persist: false },
        { name: 'memberType', type: 'member-type'},
        { name: 'idCorporate', type: 'int', reference: 'Corporate' },
        { name: 'principal', reference: 'Member'}
    ],

    hasMany: [
        {
            model: 'Member',
            name: 'dependants',
            associationKey: 'dependants'
        }
    ],
    
    validators: {
        firstName: [ { type: 'presence', message: 'This field is mandatory'}],
        surname: [ { type: 'presence', message: 'This field is mandatory'}]
    }

});
