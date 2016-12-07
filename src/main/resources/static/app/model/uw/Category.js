Ext.define('Rhino.model.uw.Category', {
    extend: 'Rhino.model.uw.Base',
    entityName: 'Category',
    idProperty: 'idCategory',
    
    fields: [
        { name: 'idCategory', type: 'int', useNull: true },
        { name: 'cat', type: 'string' },
        { name: 'description', type: 'string' },
        { name: 'idCorpAnniv', type: 'integer' },
        { name: 'corpanniv', type: 'string', persist: false,
            convert: function(v,rec){
                var data = rec.data;
                if (data.corpanniv && data.corpanniv.anniv){
                    return data.corpanniv.anniv;
                }
                return data.idCorpAnniv;
            }
        }
    ],

    hasOne: [
        {
            model: 'CorpAnniv',
            name: 'corpanniv',
            foreignKey: 'idCorpAnniv',
            associationKey: 'corpanniv'
        }
    ],

    validators: {
        cat: 'presence'
    }

});
