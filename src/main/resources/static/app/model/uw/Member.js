Ext.define('Rhino.model.uw.Member', {
    extend: 'Rhino.model.uw.Base',
    idProperty: 'idMember',
    
    fields: [
        { name: 'idMember', type: 'integer', useNull: true },
        { name: 'memberNo', type: 'string' },
        { name: 'firstName', type: 'string' },
        { name: 'surname', type: 'string' },
        { name: 'otherNames', type: 'string' },
        { name: 'sex', type: 'gender' },
        { name: 'memberType', type: 'member-type'},
        { name: 'idPrincipal', type: 'integer' },
        { name: 'principal', type: 'string', persist: false,
            convert: function(v,rec){
                var data = rec.data;
                if(data.principal && data.principal.fullName){
                    return data.principal.fullName;
                }
                return data.idPrincipal;
            }
        }

    ],

    hasOne: [
        {
            model: 'Principal',
            name: 'principal',
            foreignKey: 'idPrincipal',
            associationKey: 'principal'
        }
    ],

    validators: {
        firstName: 'presence',
        surname: 'presence'
    }

});
