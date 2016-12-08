Ext.define('Rhino.model.uw.CorpAnniv', {
    extend: 'Rhino.model.uw.Base',
    //extend: 'Ext.data.Model',
    entityName: 'CorpAnniv',
    idProperty: 'idCorpAnniv',
    //identifier: 'custom',
    
    fields: [
        { name: 'idCorpAnniv', type: 'int', useNull: true },
        { name: 'anniv', type: 'int' },
        { name: 'inception', type: 'date', dateFormat: 'Ymd' },
        { name: 'expiry', type: 'date', dateFormat: 'Ymd' },
        { name: 'renewalDate', type: 'date', dateFormat: 'Ymd' },
        { name: 'idCorporate', type: 'int'},
        { name: 'isOpen', type: 'boolean'},
        { name: 'corporate', type: 'string', persist: false,
            convert: function(v,rec){
                var data = rec.data;
                if (data.corporate && data.corporate.name){
                    return data.corporate.name;
                }
                return data.idCorporate;
            }
        },
        { name: 'idIntermediary', type: 'int'},
        { name: 'intermediary', type: 'string', persist: false,
            convert: function(v,rec){
                var data = rec.data;
                if (data.intermediary && data.intermediary.name){
                    return data.intermediary.name;
                }
                return data.idIntermediary;
            }
        }

    ],

    hasOne: [
        {
            model: 'Corporate',
            name: 'corporate',
            foreignKey: 'idCorporate',
            associationKey: 'corporate'
        },
        {
            model: 'Intermediary',
            name: 'intermediary',
            foreignKey: 'idIntermediary',
            associationKey: 'intermediary'
        }
    ],

    validators: {
        anniv: [
            { type: 'presence', message: 'This field is mandatory' }
        ],
        inception: [
            { type: 'presence', message: 'This field is mandatory' }
        ],
        expiry: [
            { type: 'presence', message: 'This field is mandatory' }
        ],
        renewalDate: [
            { type: 'presence', message: 'This field is mandatory' }
        ]
    }
});
