Ext.define('Rhino.model.uw.Corporate',{
    //TODO refactor this class to extend Rhino.model.uw.Base and comment out identifier
    extend: 'Rhino.model.uw.Base',
    entityName: 'Corporate',
    //idProperty: 'idCorporate',

    fields: [
        { name: 'id', type: 'int', mapping: 'idCorporate', useNull: true },
        { name: 'name', type: 'string' },
        { name: 'abbreviation', type: 'string' },
        { name: 'pin', type: 'string'},
        { name: 'email', type: 'auto' },
        { name: 'tel', type: 'string' },
        { name: 'postalAddress', type: 'auto' },
        { name: 'joined', type: 'date', dateFormat: 'Ymd'}

    ],
    
    validators: {
        corporateName: [
            { type: 'presence', message: 'This field is mandatory' },
            { type: 'length', min: 3, max: 20 }
        ],
        abbreviation: [
            { type: 'presence', message: 'This field is mandatory' },
            { type: 'length', min: 3, max: 3 }
        ],
        pin: [ { type: 'presence', message: 'This field is mandatory'},
            { type: 'format', matcher: /^(A|P)\d{9}\S{1}$/, message: 'PIN must be in the format A123456789A' }
        ],
        email: [
            { type: 'presence', message: 'This field is mandatory' },
            { type: 'length', min:5, max:40 } ,
            { type: 'email' }
        ],
        tel: [
            { type: 'presence', message: 'This field is mandatory' },
            { type: 'length', min:5, max:40 }
        ],
        joined: 'presence'
    }
    

});