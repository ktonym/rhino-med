Ext.define('Rhino.model.care.Admission',{
    extend: 'Rhino.model.Base',
    entityName: 'Admission',

    fields: [
        {name: 'id', type: 'int', useNull: true},
        {name: 'memberNo', type: 'string'},
        {name: 'idProvider', type: 'int'},
        {name: 'hospital', type: 'string'},
        {name: 'admissionDate', type: 'date', dateFormat: 'Ymd'},
        {name: 'idDiagnosis', type: 'int'},
        {name: 'diagnosis', type: 'string'}
    ],
    
    validators: {
        memberNo: [
            { type: 'presence', message: 'This field is mandatory' },
            { type: 'length', min: 3, max: 20 }
        ]
    }

    
});