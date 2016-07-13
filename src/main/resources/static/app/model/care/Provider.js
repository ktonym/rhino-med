Ext.define('Rhino.model.care.Provider',{
    extend: 'Rhino.model.care.Base',
    entityName: 'Provider',

    fields: [
        {name: 'idProvider', type: 'int', useNull: true},
        {name: 'providerName', type: 'string'},
        {name: 'tel', type: 'string'},
        {name: 'town', type: 'string'},
        {name: 'email', type: 'string'}
    ],
    idProperty: 'idProvider',

    validators: {
        providerName: [{ type: 'presence', message: 'This field is mandatory' }],
        tel: [{ type: 'presence', message: 'This field is mandatory' }],
        email: [
            { type: 'presence', message: 'This field is mandatory' },
            //{ type: 'length', min:5, max:40 } ,
            { type: 'email' }
        ]
    }
    
    
});