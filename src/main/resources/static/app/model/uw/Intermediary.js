Ext.define('Rhino.model.uw.Intermediary',{
    extend: 'Rhino.model.uw.Base',
    entityName: 'Intermediary',
    idProperty: 'idIntermediary',
    requires: ['Rhino.fields.Pin','Rhino.fields.IntermediaryType'],
    //identifier: 'custom',
    fields: [
        { name: 'idIntermediary', type: 'int', useNull: true },
        { name: 'pin', type: 'pin' },
        { name: 'name', type: 'string' },
        { name: 'type', type: 'intermediarytype' },
        { name: 'joinDate', type: 'date', dateFormat: 'Ymd'},
        { name: 'email', type: 'string'},
        { name: 'tel', type: 'string'},
        { name: 'street', type: 'string' },
        { name: 'town', type: 'string'},
        { name: 'postalAddress', type: 'string' }
        ],

    validators: {
        name:     [ { type: 'presence', message: 'This field is mandatory'} ],
        type:     [ { type: 'presence', message: 'This field is mandatory'} ],
        joinDate: [ { type: 'presence', message: 'This field is mandatory'} ],
        pin:      [ { type: 'presence', message: 'This field is mandatory'} ],
        email:    [
                    { type: 'presence', message: 'This field is mandatory' },
                    { type: 'length', min:5, max:40 } ,
                    { type: 'email' }
                  ],
        tel:      [
                    { type: 'presence', message: 'This field is mandatory' },
                    { type: 'length', min:5, max:40 }
                  ]
    }
});