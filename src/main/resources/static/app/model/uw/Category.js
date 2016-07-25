Ext.define('Rhino.model.uw.Category', {
    extend: 'Rhino.model.uw.Base',
    entityName: 'Category',
    idProperty: 'idCategory',
    
    fields: [
        { name: 'idCategory', type: 'integer', useNull: true },
        { name: 'cat', type: 'string' },
        { name: 'description', type: 'string' },
        { name: 'idCorpAnniv', type: 'integer' },
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
        cat: 'presence'
    }

});
