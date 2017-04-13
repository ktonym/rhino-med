Ext.define('Rhino.model.uw.Category', {
    extend: 'Rhino.model.uw.Base',
    entityName: 'Category',
    idProperty: 'idCategory',
    
    fields: [
        { name: 'idCategory', type: 'int', useNull: true },
        { name: 'cat', type: 'string' },
        { name: 'description', type: 'string' },
        { name: 'idCorpAnniv', type: 'int'}//, reference: 'CorpAnniv' }
    ],
    
    validators: {
        cat: 'presence',
        idCorpAnniv: 'presence'
    }

});
