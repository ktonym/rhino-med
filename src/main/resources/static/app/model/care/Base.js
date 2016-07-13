Ext.define('Rhino.model.care.Base',{
    extend: 'Ext.data.Model',
    identifier: 'custom',
    schema: {
        id: 'careSchema',
        namespace: 'Rhino.model.care',
        urlPrefix: 'care',
        proxy: {
            type: 'ajax',
            api : {
                create: '{prefix}/{entityName:lowercase}/store.json',
                read: '{prefix}/{entityName:lowercase}/findAll.json',
                update: '{prefix}/{entityName:lowercase}/store.json',
                destroy: '{prefix}/{entityName:lowercase}/remove.json'
            },
            reader: {
                type: 'json',
                rootProperty: 'data'
            },
            writer: {
                type: 'json',
                writeAllFields: true,
                rootProperty: 'data',
                allowSingle: false,
                encode: true
            },
            listeners: {
                exception: function(proxy, response, operation){
                    Rhino.util.Util.showErrorMsg(response.responseText);
                }
            }
        }
    }
});