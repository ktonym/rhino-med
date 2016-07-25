Ext.define('Rhino.model.uw.Principal', {
    extend: 'Rhino.model.uw.Base',
    entityName: 'Principal',
    idProperty: 'idPrincipal',

    fields: [
        { name: 'idPrincipal', type: 'integer', useNull: true },
        { name: 'familyNo', type: 'string' },
        { name: 'firstName', type: 'string' },
        { name: 'surname', type: 'string' },
        { name: 'otherNames', type: 'string' },
        { name: 'idCorporate', type: 'integer' },
        { name: 'dob', type: 'date' },
        { name: 'fullName', type: 'string', persist: false},
        { name: 'corporate', type: 'string', persist: false,
            convert: function(v,rec){
                var data = rec.data;
                if (data.corporate && data.corporate.name){
                    return data.corporate.name;
                }
                return data.idCorporate;
            }
        }
    ],

    hasOne: [
        {
            model: 'Corporate',
            name: 'corporate',
            foreignKey: 'idCorporate',
            associationKey: 'corporate'
        }
    ],

    validators: {
        firstName: 'presence',
        surname: 'presence'
    }

});
