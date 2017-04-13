Ext.define('Rhino.model.uw.Corporate',{
    extend: 'Rhino.model.uw.Base',
    entityName: 'Corporate',
    //idProperty: 'idCorporate',
    requires: ['Rhino.fields.Pin','Rhino.fields.PlanType'],
    fields: [
        { name: 'id', type: 'int', mapping: 'idCorporate', useNull: true },
        { name: 'name', type: 'string' },
        { name: 'abbreviation', type: 'string' },
        { name: 'pin', type: 'pin' },
        { name: 'email', type: 'auto' },
        { name: 'tel', type: 'string' },
        { name: 'postalAddress', type: 'auto' },
        { name: 'planType', type: 'plan' },
        { name: 'joined', type: 'date', dateFormat: 'Ymd' }
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
        pin: [ { type: 'presence', message: 'This field is mandatory'} ],
        email: [
            { type: 'presence', message: 'This field is mandatory' },
            { type: 'email' }
        ],
        tel: [
            { type: 'presence', message: 'This field is mandatory' },
            { type: 'length', min:5, max:40 }
        ],
        joined: 'presence',
        planType: 'presence'
    }
});