Ext.define('Rhino.model.uw.Intermediary',{
    extend: 'Rhino.model.uw.Base',
    
    entityName: 'Intermediary',

    idProperty: 'idIntermediary',

    //identifier: 'custom',

    fields: [
        { name: 'idIntermediary', type: 'int', useNull: true },
        { name: 'pin', type: 'string' },
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
        pin:      [ { type: 'presence', message: 'This field is mandatory'},
                    { type: 'format', matcher: /^(A|P)\d{9}\S{1}$/, message: 'PIN must be in the format A123456789A' }
                  ],
        name:     [ { type: 'presence', message: 'This field is mandatory'} ],
        type:     [ { type: 'presence', message: 'This field is mandatory'} ],
        joinDate: 'presence',
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