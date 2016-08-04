Ext.define('Rhino.view.care.CareModel',{
    extend: 'Ext.app.ViewModel',
    
    alias: 'viewmodel.care',

    requires: ['Rhino.model.care.Provider'],
    
    stores: {
        admissions: {
            model: 'Rhino.model.care.Admission',
            data: {
                items: [
                    {"id":1, "memberNo":"KOF/0001/00", "fullName":"John Katama", "idProvider":1,"provider":"Aga Khan Kisumu", "admissionDate":"20160123", "idDiagnosis":1, "diagnosis":"Stomach ache"},
                    {"id":2, "memberNo":"KBC/0001/00", "fullName":"Kangeta Giza", "idProvider":2, "provider":"Nairobi Hospital", "admissionDate":"20160120", "idDiagnosis":1, "diagnosis":"Stomach ache"}
                ]
            },
            proxy: {
                type: 'memory',
                reader: {
                    type: 'json',
                    rootProperty: 'items'
                }
            },
            autoLoad: true
        },
        providers: {
            model: 'Rhino.model.care.Provider',
            autoLoad: true
        },
        diagnosis: {
            
        }
    }
});