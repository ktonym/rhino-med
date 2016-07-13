Ext.define('Rhino.model.uw.AnnivSusp',{
    extend: 'Rhino.model.uw.Base',

    entityName: 'AnnivSusp',

    idProperty: 'idAnnivSusp',

    fields: [
        { name: 'idAnnivSusp', type: 'int',useNull: true },
        { name: 'startDate', type: 'date', dateFormat: 'Ymd'},
        { name: 'endDate', type: 'date', dateFormat: 'Ymd' },
        { name: 'reason', type: 'string'}
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
        idAnnivSusp: [
            { type: 'presence', message: 'This field is mandatory' }
        ],
        startDate: [
            { type: 'presence', message: 'This field is mandatory' }
        ],
        endDate: [
            { type: 'presence', message: 'This field is mandatory' }
        ],
        reason: [
            { type: 'presence', message: 'This field is mandatory' }
        ]
    }

});